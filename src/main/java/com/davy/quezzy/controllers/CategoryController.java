package com.davy.quezzy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma categoria específica pelo id")
    public CategoryEntity getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id).get();
    }

    @PostMapping
    @Operation(summary = "Criar uma nova categoria")
    public CategoryEntity createCategory(@RequestBody CategoryModel category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(category.getName());
        categoryEntity.setCreatedAt(DateFormatter.getCurrentDateTime());
        categoryEntity.setUpdatedAt(DateFormatter.getCurrentDateTime());

        return categoryRepository.save(categoryEntity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma categoria existente")
    public CategoryEntity updateCategory(@PathVariable Long id, @RequestBody CategoryModel category) {
        CategoryEntity categoryToUpdate = categoryRepository.findById(id).get();
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setUpdatedAt(DateFormatter.getCurrentDateTime());

        return categoryRepository.save(categoryToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma categoria existente")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
