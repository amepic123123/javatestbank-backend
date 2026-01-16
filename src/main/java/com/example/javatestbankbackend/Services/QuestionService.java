package com.example.javatestbankbackend.Services;


import com.example.javatestbankbackend.Model.Question;
import com.example.javatestbankbackend.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repo;


    public QuestionService(QuestionRepository repo) {
        this.repo = repo;
    }

    public Question save(Question question){
        return repo.save(question);
    }

    public List<Question> getAll(){
        return repo.findAll();
    }
}
