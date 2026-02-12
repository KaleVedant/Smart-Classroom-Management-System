package com.smartclassroom.service;

import com.smartclassroom.config.JwtUtil;
import com.smartclassroom.dto.LoginRequest;
import com.smartclassroom.dto.RegisterRequest;
import com.smartclassroom.entity.User;
import com.smartclassroom.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ REGISTER
    public User register(RegisterRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists");
                });

        userRepository.findByUniqueId(request.getUniqueId())
                .ifPresent(u -> {
                    throw new RuntimeException("Unique ID already exists");
                });

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .uniqueId(request.getUniqueId())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    // ✅ LOGIN
    public String login(LoginRequest request, JwtUtil jwtUtil) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

    }


}
