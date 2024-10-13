package com.davy.quezzy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.davy.quezzy.entities.CategoryEntity;
import com.davy.quezzy.helpers.DateFormatter;
import com.davy.quezzy.models.CategoryModel;
import com.davy.quezzy.repositories.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public CategoryEntity getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id).get();
    }

    @PostMapping
    public CategoryEntity createCategory(@RequestBody CategoryModel category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(category.getName());
        categoryEntity.setCreatedAt(DateFormatter.getCurrentDateTime());
        categoryEntity.setUpdatedAt(DateFormatter.getCurrentDateTime());

        return categoryRepository.save(categoryEntity);
    }

    @PutMapping("/{id}")
    public CategoryEntity updateCategory(@PathVariable Long id, @RequestBody CategoryModel category) {
        CategoryEntity categoryToUpdate = categoryRepository.findById(id).get();
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setUpdatedAt(DateFormatter.getCurrentDateTime());

        return categoryRepository.save(categoryToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
