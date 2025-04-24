package com.expense_tracker.expense_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()           // disable CSRF
                .authorizeHttpRequests()    // allow all requests
                .anyRequest().permitAll()
                .and()
                .httpBasic().disable();     // disable basic auth

        return http.build();
    }
}
