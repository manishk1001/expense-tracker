package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.PasswordResetRequestDTO;
import com.expense_tracker.expense_tracker.dto.UserDTO;
import com.expense_tracker.expense_tracker.dto.UserResponseDTO;
import com.expense_tracker.expense_tracker.entity.PasswordOtp;
import com.expense_tracker.expense_tracker.entity.Role;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.entity.UserRole;
import com.expense_tracker.expense_tracker.exception.ResourceNotFoundException;
import com.expense_tracker.expense_tracker.exception.ResponseCodeEnum;
import com.expense_tracker.expense_tracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordOtpRepository passwordOtpRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public UserResponseDTO createUser(UserDTO userDTO, String roleName) {
        Optional<User> existingUser = userRepository.findByEmailId(userDTO.getEmailId());
        if(existingUser.isPresent()){
            logger.warn(userDTO.getEmailId() + " email Id already exists");
            throw new ResourceNotFoundException(ResponseCodeEnum.EMAIL_ALREADY_EXISTS);

        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hashing password
        user.setMonthlyIncome(userDTO.getMonthlyIncome());

        User savedUser = userRepository.save(user);
        UserRole userRole = new UserRole();
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.ROLE_NOT_FOUND));
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
        logger.info("User successfully created with email Id " + savedUser.getEmailId());
        return UserResponseDTO.builder()
                .userId(savedUser.getUserId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .emailId(savedUser.getEmailId())
                .monthlyIncome(savedUser.getMonthlyIncome())
                .build();
    }

    public UserResponseDTO getUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            logger.warn("user with userId "+ userId+" not found");
            throw new ResourceNotFoundException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        return UserResponseDTO.builder()
                .userId(user.get().getUserId())
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName())
                .emailId(user.get().getEmailId())
                .monthlyIncome(user.get().getMonthlyIncome())
                .createdAt(user.get().getCreatedAt())
                .build();
    }

    public UserResponseDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findByEmailId(userDTO.getEmailId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.USER_NOT_FOUND));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmailId(userDTO.getEmailId());
        user.setMonthlyIncome(userDTO.getMonthlyIncome());
        User savedUser = userRepository.save(user);

        return UserResponseDTO.builder()
                .userId(savedUser.getUserId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .emailId(savedUser.getEmailId())
                .monthlyIncome(savedUser.getMonthlyIncome())
                .build();
    }


    public void resetPassword(PasswordResetRequestDTO passwordResetRequestDTO) {
        String emailId = passwordResetRequestDTO.getEmailId();
        String password = passwordResetRequestDTO.getPassword();
        String otp = passwordResetRequestDTO.getOtp();
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.USER_NOT_FOUND));
        PasswordOtp passwordOtp = passwordOtpRepository.findByUserIdAndExpiryAfter(user.getUserId(),LocalDateTime.now())
                        .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.OTP_EXPIRED_OR_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        passwordOtpRepository.delete(passwordOtp);
    }

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.USER_NOT_FOUND));
        userRepository.delete(user);
        passwordOtpRepository.deleteAllByUserId(user.getUserId());
        expenseRepository.deleteAllByUserId(user.getUserId());
    }
}
