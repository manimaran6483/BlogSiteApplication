package com.api.auth.service.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.auth.service.model.User;
import com.api.auth.service.repository.UserRepository;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(String name, String email, String rawPassword) throws Exception {
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new Exception("Email already registered");
        }
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setCreatedAt(LocalDateTime.now());
        return userRepository.save(u);
    }

    public User authenticate(String email, String rawPassword) throws Exception {
        Optional<User> existing = userRepository.findByEmail(email);
        if (!existing.isPresent()) {
            throw new Exception("Invalid credentials");
        }
        User u = existing.get();
        if (!passwordEncoder.matches(rawPassword, u.getPassword())) {
            throw new Exception("Invalid credentials");
        }
        return u;
    }
}
