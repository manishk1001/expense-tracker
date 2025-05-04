package com.expense_tracker.expense_tracker.repository;

import com.expense_tracker.expense_tracker.entity.PasswordOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordOtpRepository extends JpaRepository<PasswordOtp, Long> {
//    Optional<PasswordOtp> findByUser_UserId(Long userId);

    Optional<PasswordOtp> findByUser_UserIdAndExpiryAfter(Long userId, LocalDateTime currentTime);

    void deleteAllByUser_UserId(Long userId);

    Optional<PasswordOtp> findByUser_UserId(long userId);
}