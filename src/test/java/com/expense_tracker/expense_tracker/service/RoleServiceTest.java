package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.entity.Role;
import com.expense_tracker.expense_tracker.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();
    }

    @Test
    void testFindRoleByName_Success() {
        // Mock repository find
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(testRole));

        // Execute
        Role result = roleService.findRoleByName("ROLE_USER");

        // Verify
        assertNotNull(result);
        assertEquals(testRole.getName(), result.getName());
        verify(roleRepository, times(1)).findByName(any(String.class));
    }

    @Test
    void testFindRoleByName_NotFound() {
        // Mock repository find
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        // Execute and expect exception
        assertThrows(IllegalArgumentException.class, () -> {
            roleService.findRoleByName("ROLE_NONEXISTENT");
        });

        verify(roleRepository, times(1)).findByName(any(String.class));
    }

    @Test
    void testSaveRole() {
        // Mock repository save
        when(roleRepository.save(any(Role.class))).thenReturn(testRole);

        // Execute
        Role result = roleService.saveRole(testRole);

        // Verify
        assertNotNull(result);
        assertEquals(testRole.getName(), result.getName());
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void testGetAllRoles() {
        // Mock repository find all
        when(roleRepository.findAll()).thenReturn(List.of(testRole));

        // Execute
        List<Role> result = roleService.getAllRoles();

        // Verify
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(roleRepository, times(1)).findAll();
    }
}
