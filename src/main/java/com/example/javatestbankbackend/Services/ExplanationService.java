package com.example.javatestbankbackend.Services;


import com.example.javatestbankbackend.Model.Question;
import org.springframework.stereotype.Service;

@Service
public class ExplanationService {

    public String generateExplanation(Question q, String userAnswer){
        return "Correct answer is " + q.getCorrectOption() + ". You answered " + userAnswer + ", which is incorrect ";
    }

}
