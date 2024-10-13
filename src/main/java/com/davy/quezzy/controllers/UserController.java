package com.davy.quezzy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.davy.quezzy.entities.UserEntity;
import com.davy.quezzy.helpers.errors.NotFound;
import com.davy.quezzy.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(
            () -> new NotFound("User " + id + " not found")
        );
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        UserEntity userToUpdate = userRepository.findById(id).get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
