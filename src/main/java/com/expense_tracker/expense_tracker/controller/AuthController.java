package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.config.JwtUtil;
import com.expense_tracker.expense_tracker.dto.AuthRequest;
import com.expense_tracker.expense_tracker.dto.AuthResponse;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmailId(request.getEmail()).orElseThrow(() -> new RuntimeException("Email Id not Found"));

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmailId());
        return ResponseEntity.ok(new AuthResponse(token));

    }
}