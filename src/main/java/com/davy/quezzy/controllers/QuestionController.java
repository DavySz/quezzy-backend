package com.davy.quezzy.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<QuestionEntity>> getQuestions() {
        List<QuestionEntity> questions = questionRepository.findAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma questão específica pelo id")
    public ResponseEntity<QuestionEntity> getQuestionById(@PathVariable Long id) {    
        Optional<QuestionEntity> response = questionRepository.findById(id);
        if(response.isPresent()){
            QuestionEntity category = response.get();
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Buscar questões por categoria")
    public ResponseEntity<List<QuestionEntity>> getQuestionsByCategory(@PathVariable Long categoryId) {
        List<QuestionEntity> questions = questionRepository.findQuestionsByCategoryId(categoryId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova questão")
    public ResponseEntity<QuestionEntity> createQuestion(@RequestBody QuestionModel question) {
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
        
        QuestionEntity createdQuestion = questionRepository.save(questionEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma questão existente")
    public ResponseEntity<QuestionEntity> updateQuestion(@PathVariable Long id, @RequestBody QuestionModel question) {
        Optional<QuestionEntity> response = questionRepository.findById(id);
        
        if(response.isPresent()) {
            QuestionEntity questionToUpdate = response.get();
            questionToUpdate.setUpdatedAt(DateFormatter.getCurrentDateTime());
            questionToUpdate.setQuestion(question.getQuestion());
            questionToUpdate.setAnswer(question.getAnswer());
            questionToUpdate.setCategoryId(question.getCategoryId());
            questionToUpdate.setCreatorId(question.getCreatorId());
            questionToUpdate.setDifficulty(question.getDifficulty());
            questionToUpdate.setWrongAnswer1(question.getWrongAnswer1());
            questionToUpdate.setWrongAnswer2(question.getWrongAnswer2());
            questionToUpdate.setWrongAnswer3(question.getWrongAnswer3());
            questionRepository.save(questionToUpdate);
    
            return ResponseEntity.ok(questionToUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma questão existente")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        Optional<QuestionEntity> response = questionRepository.findById(id);
        if (response.isPresent()) {
            questionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
