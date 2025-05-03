package com.expense_tracker.expense_tracker.config;

import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.entity.UserRole;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import com.expense_tracker.expense_tracker.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<UserRole> userRoles = userRoleRepository.findByUserUserId(user.getUserId());

        List<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getRoleName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmailId(),
                user.getPassword(),
                authorities
        );
    }
} 