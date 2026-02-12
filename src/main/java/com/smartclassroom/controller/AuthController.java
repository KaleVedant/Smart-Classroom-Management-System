package com.smartclassroom.controller;

import com.smartclassroom.config.JwtUtil;
import com.smartclassroom.dto.LoginRequest;
import com.smartclassroom.dto.RegisterRequest;
import com.smartclassroom.entity.User;
import com.smartclassroom.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // ✅ constructor injection
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // ✅ INSTANCE method call
        String token = userService.login(request, jwtUtil);
        return ResponseEntity.ok(token);
    }
}
