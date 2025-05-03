package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.entity.Role;
import com.expense_tracker.expense_tracker.exception.ResourceNotFoundException;
import com.expense_tracker.expense_tracker.exception.ResponseCodeEnum;
import com.expense_tracker.expense_tracker.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role updatedRole) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setRoleName(updatedRole.getRoleName());
                    return roleRepository.save(role);
                })
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.ROLE_NOT_FOUND));
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

