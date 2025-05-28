package com.example.gradle.ServiceImpl;

import com.example.gradle.Service.KafkaConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    @Override
    @KafkaListener(topics = "employee-topic", groupId = "employee-group")
    public void consume(String message) {
        System.out.println("Consumed message from Kafka: " + message);
    }
}
