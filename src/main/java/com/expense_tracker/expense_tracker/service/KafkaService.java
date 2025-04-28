package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.PasswordResetEvent;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class KafkaService {
    @Value("${spring.kafka.topic}")
    private String TOPIC;
    @Autowired
    private KafkaTemplate<String, PasswordResetEvent> kafkaTemplate;
    public void sendEmailEvent(PasswordResetEvent passwordResetEvent) {
        kafkaTemplate.send(TOPIC, passwordResetEvent);
    }
}

