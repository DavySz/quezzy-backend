package com.davy.quezzy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.davy.quezzy.entities.UserEntity;
import com.davy.quezzy.helpers.DateFormatter;
import com.davy.quezzy.models.UserModel;
import com.davy.quezzy.repositories.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário especifo pelo id")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).get();
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    public UserEntity createUser(@RequestBody UserModel user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setCreatedAt(DateFormatter.getCurrentDateTime());
        userEntity.setUpdatedAt(DateFormatter.getCurrentDateTime());

        return userRepository.save(userEntity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        UserEntity userToUpdate = userRepository.findById(id).get();
        userToUpdate.setUpdatedAt(DateFormatter.getCurrentDateTime());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());

        return userRepository.save(userToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário existente")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
