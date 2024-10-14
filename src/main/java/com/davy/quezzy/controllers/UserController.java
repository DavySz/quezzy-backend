package com.davy.quezzy.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário especifo pelo id")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> response = userRepository.findById(id);
        if(response.isPresent()){
            UserEntity user = response.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserModel user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setCreatedAt(DateFormatter.getCurrentDateTime());
        userEntity.setUpdatedAt(DateFormatter.getCurrentDateTime());

        UserEntity createdUser = userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        Optional<UserEntity> response = userRepository.findById(id);
        if(response.isPresent()) {
            UserEntity userToUpdate = response.get();
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setUpdatedAt(DateFormatter.getCurrentDateTime());
            UserEntity updatedUser = userRepository.save(userToUpdate);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário existente")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<UserEntity> response = userRepository.findById(id);
        if (response.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
