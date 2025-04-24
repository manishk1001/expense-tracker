package com.expense_tracker.expense_tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private Double monthlyIncome;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
}
