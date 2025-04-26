package com.expense_tracker.expense_tracker.dto;

import com.expense_tracker.expense_tracker.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long expenseId;
    private String description;
    private Double amount;
    private Category category;
    private LocalDateTime createdAt;
}
