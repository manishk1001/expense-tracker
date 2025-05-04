package com.expense_tracker.expense_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Builder
@Entity
@Table(name = "PasswordOtp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private String otp;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expiry;
}
