package com.example.javatestbankbackend.DTO;

public class QuizResult {

    private boolean correct;
    private String explanation;

    public QuizResult(boolean correct, String explanation) {
        this.correct = correct;
        this.explanation = explanation;
    }

    public boolean isCorrect() {
        return correct;
    }
    public String getExplanation() {
        return explanation;
    }
}
