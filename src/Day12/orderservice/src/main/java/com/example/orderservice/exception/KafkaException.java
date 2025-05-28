package com.example.orderservice.exception;

public class KafkaException extends RuntimeException {
    public KafkaException(String message) {
        super(message);
    }
}
