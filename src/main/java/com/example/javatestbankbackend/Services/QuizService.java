package com.example.javatestbankbackend.Services;


import com.example.javatestbankbackend.DTO.AnswerRequest;
import com.example.javatestbankbackend.DTO.QuizResult;
import com.example.javatestbankbackend.Model.Question;
import com.example.javatestbankbackend.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuestionRepository repo;
    private final ExplanationService explanationService;

    public QuizService(QuestionRepository repo,
                       ExplanationService explanationService) {

        this.repo = repo;
        this.explanationService = explanationService;
    }

    public QuizResult checkAnswer(AnswerRequest req){
        Question q = repo.findById(req.getQuestionId()).orElseThrow();

        boolean isCorrect = q.getCorrectOption().equalsIgnoreCase(req.getUserAnswer());
        String explanation = q.getExplanation();

        if(explanation == null || explanation.isBlank()){
           explanation = explanationService.generateExplanation(q, req.getUserAnswer());
           q.setExplanation(explanation);
              repo.save(q);
        }
        return new QuizResult(isCorrect, explanation);
    }

}
