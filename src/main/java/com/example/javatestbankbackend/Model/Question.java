package com.example.javatestbankbackend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Question {

    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private String correctOption;
    private String topic;

    @Column(length = 2000)
    private String explanation;


}
