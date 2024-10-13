package com.davy.quezzy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.davy.quezzy.entities.QuestionEntity;
import com.davy.quezzy.helpers.DateFormatter;
import com.davy.quezzy.models.QuestionModel;
import com.davy.quezzy.repositories.QuestionRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Questões", description = "Operações relacionadas a questões")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    @Operation(summary = "Listar todas as questões")
    public List<QuestionEntity> getQuestions() {
        return questionRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma questão específica pelo id")
    public QuestionEntity getQuestionById(@PathVariable Long id) {
        return questionRepository.findById(id).get();
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Buscar questões por categoria")
    public List<QuestionEntity> getQuestionsByCategory(@PathVariable Long categoryId) {
        return questionRepository.findQuestionsByCategoryId(categoryId);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova questão")
    public QuestionEntity createQuestion(@RequestBody QuestionModel question) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestion(question.getQuestion());
        questionEntity.setAnswer(question.getAnswer());
        questionEntity.setCategoryId(question.getCategoryId());
        questionEntity.setCreatorId(question.getCreatorId());
        questionEntity.setDifficulty(question.getDifficulty());
        questionEntity.setCreatedAt(DateFormatter.getCurrentDateTime());
        questionEntity.setUpdatedAt(DateFormatter.getCurrentDateTime());
        questionEntity.setWrongAnswer1(question.getWrongAnswer1());
        questionEntity.setWrongAnswer2(question.getWrongAnswer2());
        questionEntity.setWrongAnswer3(question.getWrongAnswer3());
        
        return questionRepository.save(questionEntity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma questão existente")
    public QuestionEntity updateQuestion(@PathVariable Long id, @RequestBody QuestionModel question) {
        QuestionEntity questionToUpdate = this.getQuestionById(id);
        questionToUpdate.setQuestion(question.getQuestion());
        questionToUpdate.setAnswer(question.getAnswer());
        questionToUpdate.setCategoryId(question.getCategoryId());
        questionToUpdate.setCreatorId(question.getCreatorId());
        questionToUpdate.setDifficulty(question.getDifficulty());
        questionToUpdate.setUpdatedAt(DateFormatter.getCurrentDateTime());
        questionToUpdate.setWrongAnswer1(question.getWrongAnswer1());
        questionToUpdate.setWrongAnswer2(question.getWrongAnswer2());
        questionToUpdate.setWrongAnswer3(question.getWrongAnswer3());

        return questionRepository.save(questionToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma questão existente")
    public void deleteQuestion(@PathVariable Long id) {
        questionRepository.deleteById(id);
    }
}
