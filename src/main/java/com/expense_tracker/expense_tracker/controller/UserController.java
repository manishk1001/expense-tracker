package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.dto.UserDTO;
import com.expense_tracker.expense_tracker.dto.UserResponseDTO;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
    @GetMapping
    public UserResponseDTO getUser(Principal principal) {
        String emailId = principal.getName();
        return userService.getUser(emailId);
    }

    @PutMapping
    public UserResponseDTO updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        return userService.updateUser(userDTO);
    }

}
