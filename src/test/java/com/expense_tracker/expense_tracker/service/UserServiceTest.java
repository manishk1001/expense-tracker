package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.UserDto;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .email("test@example.com")
                .build();

        testUserDto = UserDto.builder()
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .build();
    }

    @Test
    void testCreateUser_Success() {
        // Mock the password encoder
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        
        // Mock the repository save
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Execute
        UserDto result = userService.createUser(testUserDto);

        // Verify
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

    @Test
    void testCreateUser_UsernameExists() {
        // Mock user already exists
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(testUser));

        // Execute and expect exception
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(testUserDto);
        });

        // Verify no save was called
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any(String.class));
    }

    @Test
    void testFindUserByUsername_Success() {
        // Mock user exists
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(testUser));

        // Execute
        UserDto result = userService.findUserByUsername("testuser");

        // Verify
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByUsername(any(String.class));
    }

    @Test
    void testFindUserByUsername_NotFound() {
        // Mock user not found
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // Execute and expect exception
        assertThrows(IllegalArgumentException.class, () -> {
            userService.findUserByUsername("nonexistentuser");
        });

        verify(userRepository, times(1)).findByUsername(any(String.class));
    }
}
