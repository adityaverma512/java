package com.example.gradle.kafka;

import com.example.gradle.DTO.EmployeeDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeKafkaConsumer {

    @KafkaListener(topics = "employee-events", groupId = "notification-service-group")
    public void consumeEmployeeEvent(EmployeeDto employee) {
        System.out.println("Received employee event: " + employee);
    }
}
