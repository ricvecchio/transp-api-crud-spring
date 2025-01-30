package com.transportadora.user.service;

import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User execute(User user) {

        User existsUser = userRepository.findByUsername(user.getUsername());

        if (existsUser != null) {
            throw new Error("Usuário já existe!");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}