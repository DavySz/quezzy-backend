package com.davy.quezzy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.davy.quezzy.entities.QuestionEntity;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query("SELECT q FROM QuestionEntity q WHERE q.categoryId = :categoryId")
    List<QuestionEntity> findQuestionsByCategoryId(Long categoryId);
}
