package com.expense_tracker.expense_tracker.dto;

import lombok.Data;

@Data
public class ExpenseDTO {
    private Long expenseId;
    private Long userId;
    private String description;
    private Double amount;
}
