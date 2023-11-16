package com.example.publisher.controllers;

import com.example.publisher.dto.UserDTO;
import com.example.publisher.models.UserEntity;
import com.example.publisher.repository.UserRepository;
import com.example.publisher.security.JwtCore;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class SecurityController {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtCore jwtCore;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @PostMapping("/signin")
    ResponseEntity<?> signin(){}

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        if(userRepository.existsUserEntityByEmail(userDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account with this email already exists");
        }

        UserEntity user = new UserEntity();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setBirthdate(userDTO.getBirthdate());
        user.setPassword(userDTO.getPassword());
        return ResponseEntity.ok("Success, nice work");
    }
}
