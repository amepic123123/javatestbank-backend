package com.example.javatestbankbackend.Services;

import com.example.javatestbankbackend.Model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExplanationService {

    @Value("${ai.api.key:}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // =========================
    // PUBLIC METHOD
    // =========================
    public String generateExplanation(Question q, String userAnswer) {

        try {
            if (apiKey == null || apiKey.isBlank()) {
                return "Explanation unavailable (API key missing).";
            }

            String prompt = """
            Question: %s

            Options:
            A) %s
            B) %s
            C) %s
            D) %s

            Correct Answer: %s
            User Answer: %s

            Explain in MAX 4 short sentences:
            - why the correct answer is right
            - why the user's answer is wrong

            Keep it simple and exam-style. No long paragraphs.
            """.formatted(
                    q.getQuestionText(),
                    q.getOptionA(),
                    q.getOptionB(),
                    q.getOptionC(),
                    q.getOptionD(),
                    q.getCorrectOption(),
                    userAnswer
            );

            String explanation = callGroq(prompt);

            // Safety net: trim if AI still talks too much
            if (explanation != null && explanation.length() > 500) {
                explanation = explanation.substring(0, 500) + "...";
            }

            return explanation;

        } catch (Exception e) {
            e.printStackTrace();
            return "Explanation unavailable (AI error).";
        }
    }

    // =========================
    // AI CALL
    // =========================
    private String callGroq(String prompt) throws Exception {

        // -------- Build request body safely --------
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "llama-3.1-8b-instant");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful tutor."));
        messages.add(Map.of("role", "user", "content", prompt));

        bodyMap.put("messages", messages);
        bodyMap.put("temperature", 0.2);
        bodyMap.put("max_tokens", 120);

        String json = mapper.writeValueAsString(bodyMap);

        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://api.groq.com/openai/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        // -------- Execute request --------
        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                String err = response.body() != null ? response.body().string() : "no body";
                return "AI ERROR: " + response.code() + " → " + err;
            }

            String result = response.body().string();

            // -------- Parse response --------
            JsonNode root = mapper.readTree(result);

            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
        }
    }
}
