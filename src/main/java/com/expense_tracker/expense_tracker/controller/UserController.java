package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.dto.PasswordResetEvent;
import com.expense_tracker.expense_tracker.dto.PasswordResetRequestDTO;
import com.expense_tracker.expense_tracker.dto.UserDTO;
import com.expense_tracker.expense_tracker.dto.UserResponseDTO;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.service.EmailService;
import com.expense_tracker.expense_tracker.service.KafkaService;
import com.expense_tracker.expense_tracker.service.PasswordOtpService;
import com.expense_tracker.expense_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final  PasswordOtpService passwordOtpService;
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO, "USER"));
    }
    @GetMapping
    public ResponseEntity<UserResponseDTO> getUser(Principal principal) {
        String userId = principal.getName();
        return ResponseEntity.ok(userService.getUser(Long.valueOf(userId)));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    @GetMapping("/forgot-password/{emailId}")
    public ResponseEntity<String> forgotPassord(@PathVariable String emailId) {
        passwordOtpService.sendOtpEmail(emailId);
        return ResponseEntity.ok("Sent OTP successfully !!");
    }
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO){
        userService.resetPassword(passwordResetRequestDTO);
        return ResponseEntity.ok("Password reset successfully !!");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(Authentication authentication){
        String userId = authentication.getName();
        userService.deleteUser(Long.valueOf(userId));
        return ResponseEntity.ok("User with userId "+userId+" deleted successfully !!");
    }
}
