package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.entity.UserRole;
import com.expense_tracker.expense_tracker.exception.ResourceNotFoundException;
import com.expense_tracker.expense_tracker.exception.ResponseCodeEnum;
import com.expense_tracker.expense_tracker.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> getUserRoleById(Long id) {
        return userRoleRepository.findById(id);
    }

    public List<UserRole> getUserRolesByUserId(Long userId) {
        return userRoleRepository.findByUserUserId(userId);
    }

    public List<UserRole> getUserRolesByRoleId(Long roleId) {
        return userRoleRepository.findByRoleRoleId(roleId);
    }

    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserRole updateUserRole(Long id, UserRole updatedUserRole) {
        return userRoleRepository.findById(id)
                .map(userRole -> {
                    userRole.setUser(updatedUserRole.getUser());
                    userRole.setRole(updatedUserRole.getRole());
                    userRole.setCreatedBy(updatedUserRole.getCreatedBy());
                    userRole.setUpdatedBy(updatedUserRole.getUpdatedBy());
                    userRole.setStatus(updatedUserRole.getStatus());
                    return userRoleRepository.save(userRole);
                })
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.USERROLE_NOT_FOUND));
    }

    public void deleteUserRole(Long id) {
        userRoleRepository.deleteById(id);
    }
}

