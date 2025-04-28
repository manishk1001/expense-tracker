package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.dto.PasswordResetEvent;
import com.expense_tracker.expense_tracker.dto.UserDTO;
import com.expense_tracker.expense_tracker.dto.UserResponseDTO;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.service.EmailService;
import com.expense_tracker.expense_tracker.service.KafkaService;
import com.expense_tracker.expense_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final KafkaService kafkaService;
    private final EmailService emailService;
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }
    @GetMapping
    public ResponseEntity<UserResponseDTO> getUser(Principal principal) {
        String emailId = principal.getName();
        return ResponseEntity.ok(userService.getUser(emailId));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    @GetMapping("/forgot-password")
    public void forgotPassord() {
        //kafkaService.sendEmailEvent(new PasswordResetEvent("m@a", "123"));
        String body = "You have requested for a Password reset. so here is your OTP.";
//        emailService.sendEmail("manishhh.10@gmail.com","Password Reset",body);
        PasswordResetEvent passwordResetEvent = PasswordResetEvent.builder()
                .code("12345")
                .email("manishhh.10@gmail.com")
                .build();
        kafkaService.sendEmailEvent(passwordResetEvent);
    }

}
