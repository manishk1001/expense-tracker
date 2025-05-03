package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.entity.UserRole;
import com.expense_tracker.expense_tracker.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {


    private final UserRoleService userRoleService;

    @GetMapping
    public List<UserRole> getAllUserRoles() {
        return userRoleService.getAllUserRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getUserRoleById(@PathVariable Long id) {
        return userRoleService.getUserRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<UserRole> getUserRolesByUserId(@PathVariable Long userId) {
        return userRoleService.getUserRolesByUserId(userId);
    }

    @GetMapping("/role/{roleId}")
    public List<UserRole> getUserRolesByRoleId(@PathVariable Long roleId) {
        return userRoleService.getUserRolesByRoleId(roleId);
    }

    @PostMapping
    public UserRole createUserRole(@RequestBody UserRole userRole) {
        return userRoleService.createUserRole(userRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRole> updateUserRole(@PathVariable Long id, @RequestBody UserRole userRole) {
        try {
            UserRole updated = userRoleService.updateUserRole(id, userRole);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        userRoleService.deleteUserRole(id);
        return ResponseEntity.noContent().build();
    }
}
