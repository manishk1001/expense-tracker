package com.expense_tracker.expense_tracker.entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
    private Long userId;
    private String description;
    private Double amount;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
}