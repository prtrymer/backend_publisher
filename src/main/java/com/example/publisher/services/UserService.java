package com.example.publisher.services;

import com.example.publisher.models.UserEntity;
import com.example.publisher.repository.UserRepository;
import com.example.publisher.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntityByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with email: '%s' not found", email)
        ));
        return UserDetailsImpl.build(user);
    }
}
