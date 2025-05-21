package com.example.gradle.kafka;

import com.example.gradle.DTO.EmployeeDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeKafkaProducer {

    private static final String TOPIC = "employee-events";

    private final KafkaTemplate<String, EmployeeDto> kafkaTemplate;

    public EmployeeKafkaProducer(KafkaTemplate<String, EmployeeDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmployeeCreatedEvent(EmployeeDto employee) {
        System.out.println("Sending employee event: " + employee);
        kafkaTemplate.send(TOPIC, employee);
    }
}
