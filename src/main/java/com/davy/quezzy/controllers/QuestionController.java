package com.davy.quezzy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.davy.quezzy.entities.QuestionEntity;
import com.davy.quezzy.repositories.QuestionRepository;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public List<QuestionEntity> getQuestions() {
        return questionRepository.findAll();
    }

    @GetMapping("/{id}")
    public QuestionEntity getQuestionById(@PathVariable Long id) {
        return questionRepository.findById(id).get();
    }

    @PostMapping
    public QuestionEntity createQuestion(@RequestBody QuestionEntity question) {
        return questionRepository.save(question);
    }

    @PutMapping("/{id}")
    public QuestionEntity updateQuestion(@PathVariable Long id, @RequestBody QuestionEntity question) {
        QuestionEntity questionToUpdate = this.getQuestionById(id);
        questionToUpdate.setQuestion(question.getQuestion());
        questionToUpdate.setAnswer(question.getAnswer());
        questionToUpdate.setCategory(question.getCategory());
        return questionRepository.save(questionToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
    }
}
