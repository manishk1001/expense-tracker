package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.UserDTO;
import com.expense_tracker.expense_tracker.dto.UserResponseDTO;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hashing password
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

    public UserResponseDTO getUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return UserResponseDTO.builder()
                .userId(user.get().getUserId())
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName())
                .emailId(user.get().getEmailId())
                .monthlyIncome(user.get().getMonthlyIncome())
                .build();
    }

    public UserResponseDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getUserId()));

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


}
