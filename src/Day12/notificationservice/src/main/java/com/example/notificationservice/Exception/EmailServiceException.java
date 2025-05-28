package com.example.notificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class EmailServiceException extends RuntimeException{
    public EmailServiceException(String message) {
        super(message);
    }
}
