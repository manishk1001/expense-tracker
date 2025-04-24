package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.dto.UserDTO;
import com.expense_tracker.expense_tracker.dto.UserResponseDTO;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/")
    public UserResponseDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

}
