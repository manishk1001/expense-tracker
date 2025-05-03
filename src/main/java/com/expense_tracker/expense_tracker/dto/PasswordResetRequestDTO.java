package com.expense_tracker.expense_tracker.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PasswordResetRequestDTO {
    private String emailId;
    private String password;
    private String otp;
}
