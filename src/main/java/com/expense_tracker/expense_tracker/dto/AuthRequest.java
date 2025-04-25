package com.expense_tracker.expense_tracker.dto;


import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;

    // Getters and Setters
}
