package com.davy.quezzy.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.davy.quezzy.entities.CategoryEntity;
import com.davy.quezzy.helpers.DateFormatter;
import com.davy.quezzy.models.CategoryModel;
import com.davy.quezzy.repositories.CategoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorias", description = "Operações relacionadas a categorias")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "Listar todas as categorias")
    public ResponseEntity<List<CategoryEntity>> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma categoria específica pelo id")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long id) {
        Optional<CategoryEntity> response = categoryRepository.findById(id);
        if(response.isPresent()){
            CategoryEntity category = response.get();
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar uma nova categoria")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryModel category) {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName(category.getName());
        categoryEntity.setCreatedAt(DateFormatter.getCurrentDateTime());
        categoryEntity.setUpdatedAt(DateFormatter.getCurrentDateTime());
        CategoryEntity createdCategory = categoryRepository.save(categoryEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma categoria existente")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long id, @RequestBody CategoryModel category) {
        Optional<CategoryEntity> response = categoryRepository.findById(id);

        if(response.isPresent()) {
            CategoryEntity categoryToUpdate = response.get();
            categoryToUpdate.setUpdatedAt(DateFormatter.getCurrentDateTime());
            categoryToUpdate.setName(category.getName());
            categoryRepository.save(categoryToUpdate);
    
            return ResponseEntity.ok(categoryToUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma categoria existente")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Optional<CategoryEntity> response = categoryRepository.findById(id);
        if (response.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
