package com.example.javatestbankbackend.Controller;

import com.example.javatestbankbackend.DTO.AnswerRequest;
import com.example.javatestbankbackend.DTO.QuizResult;
import com.example.javatestbankbackend.Services.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*") // allow frontend later
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // =========================
    // POST /api/quiz/answer
    // =========================
    @PostMapping("/answer")
    public QuizResult answer(@RequestBody AnswerRequest request) {
        return quizService.checkAnswer(request);
    }
}
