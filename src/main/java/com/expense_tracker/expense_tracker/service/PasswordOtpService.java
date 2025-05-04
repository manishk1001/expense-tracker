package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.PasswordResetEvent;
import com.expense_tracker.expense_tracker.entity.PasswordOtp;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.exception.ResourceNotFoundException;
import com.expense_tracker.expense_tracker.exception.ResponseCodeEnum;
import com.expense_tracker.expense_tracker.repository.PasswordOtpRepository;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordOtpService {
    private final KafkaService kafkaService;
    private final UserRepository userRepository;
    private final PasswordOtpRepository passwordOtpRepository;
    private final SecureRandom secureRandom;
    private final int OTP_LENGTH = 6;
    public void sendOtpEmail(String emailId){
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.USER_NOT_FOUND));
        String code = OtpGenerator();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(10);
        Optional<PasswordOtp> passwordOtpOptional = passwordOtpRepository.findByUser_UserId(user.getUserId());
        if(passwordOtpOptional.isPresent()){
            PasswordOtp passwordOtp = passwordOtpOptional.get();
            passwordOtp.setCreatedAt(LocalDateTime.now());
            passwordOtp.setOtp(code);
            passwordOtp.setExpiry(expiry);
            passwordOtpRepository.save(passwordOtp);
        }
        else{
            PasswordOtp passwordOtp = PasswordOtp.builder()
                    .user(user)
                    .otp(code)
                    .expiry(expiry).
                    build();
            passwordOtpRepository.save(passwordOtp);
        }
        PasswordResetEvent passwordResetEvent = PasswordResetEvent.builder()
                .code(code)
                .email(emailId)
                .build();
        kafkaService.sendEmailEvent(passwordResetEvent);
    }
    public String OtpGenerator(){
            StringBuilder otp = new StringBuilder();
            for (int i = 0; i < OTP_LENGTH; i++) {
                otp.append(secureRandom.nextInt(10));
            }
            return otp.toString();
        }
}

