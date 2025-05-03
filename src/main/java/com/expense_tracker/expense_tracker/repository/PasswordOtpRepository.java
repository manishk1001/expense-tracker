package com.expense_tracker.expense_tracker.repository;

import com.expense_tracker.expense_tracker.entity.PasswordOtp;
import com.expense_tracker.expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordOtpRepository extends JpaRepository<PasswordOtp, Long> {
    Optional<PasswordOtp> findByUserId(Long userId);

    Optional<PasswordOtp> findByUserIdAndExpiryAfter(Long userId, LocalDateTime currentTime);

    void deleteAllByUserId(Long userId);
}