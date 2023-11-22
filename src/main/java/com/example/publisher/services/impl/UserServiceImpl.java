package com.example.publisher.services.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.publisher.models.UserEntity;
import com.example.publisher.repository.RoleRepository;
import com.example.publisher.repository.UserRepository;
import com.example.publisher.security.JwtTokenProvider;
import com.example.publisher.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${aws.bucket}")
    private String bucket;

    @Override
    @Transactional
    public Optional<DecodedJWT> signIn(String username, String password) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User with username %s not found".formatted(username))
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return jwtTokenProvider.toDecodedJWT(
                jwtTokenProvider.generateToken(user.getId(), username, List.copyOf(user.getRoles())));
    }

    @Override
    @Transactional
    public UserEntity signUp(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(
                    "Username %s is already in use".formatted(user.getUsername()));
        }
        user.addRole(roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new RoleNotFoundException("Role 'USER' not found. Failed to assign to new user")
        ));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserEntity update(UserEntity user) {
        if (isUsernameInUse(user)) {
            throw new UserAlreadyExistsException(
                    "Username %s is already in use".formatted(user.getUsername()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    private boolean isUsernameInUse(UserEntity user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(found -> !found.getId().equals(user.getId())).isPresent();
    }
}

