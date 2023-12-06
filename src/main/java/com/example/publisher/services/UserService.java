package com.example.publisher.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.publisher.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<DecodedJWT> signIn(String username, String password);

    UserEntity signUp(UserEntity user);

    UserEntity update(UserEntity user, String newPassword);

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByUsername(String username);

    void deleteById(Long userId);
}
