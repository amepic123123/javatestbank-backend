package com.example.javatestbankbackend.Controller;


import com.example.javatestbankbackend.Model.Question;
import com.example.javatestbankbackend.Services.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController (QuestionService service) {
        this.service = service;
    }


    @PostMapping
    public Question add(@RequestBody Question question) {
        return service.save(question);
    }

    @GetMapping
    public List<Question> getAll() {
        return service.getAll();
    }
}
