package com.example.javatestbankbackend.DTO;

public class AnswerRequest {

    private Long questionId;
    private String userAnswer;

    public Integer getQuestionId() {
        return Math.toIntExact(questionId);
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
