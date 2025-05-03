package com.expense_tracker.expense_tracker.repository;

import com.expense_tracker.expense_tracker.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserUserId(Long userId);
    List<UserRole> findByRoleRoleId(Long roleId);
    Optional<UserRole> findByUserUserIdAndRoleRoleId(Long userId, Long roleId);
}
