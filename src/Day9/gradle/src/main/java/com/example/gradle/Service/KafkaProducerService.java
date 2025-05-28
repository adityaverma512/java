package com.example.gradle.Service;

public interface KafkaProducerService {
    void sendMessage(String topic, String message);
}
