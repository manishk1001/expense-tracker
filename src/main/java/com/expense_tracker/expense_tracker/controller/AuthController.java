package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.config.JwtUtil;
import com.expense_tracker.expense_tracker.dto.AuthRequest;
import com.expense_tracker.expense_tracker.dto.AuthResponse;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.entity.UserRole;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import com.expense_tracker.expense_tracker.repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmailId(request.getEmail()).orElseThrow(() -> new RuntimeException("Email Id not Found"));
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        List<UserRole> userRoles = userRoleRepository.findByUserUserId(user.getUserId());
        List<String> roles = userRoles.stream()
                .map(userRole -> userRole.getRole().getRoleName())
                .collect(Collectors.toList());
        String token = jwtUtil.generateToken(user.getEmailId(), user.getUserId(), roles);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}