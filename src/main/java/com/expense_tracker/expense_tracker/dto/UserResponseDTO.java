package com.expense_tracker.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private Double monthlyIncome;
}