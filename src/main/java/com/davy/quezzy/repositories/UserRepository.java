package com.davy.quezzy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davy.quezzy.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {}
