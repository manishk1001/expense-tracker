package com.expense_tracker.expense_tracker.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private Double monthlyIncome;
}