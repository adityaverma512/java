package com.example.gradle.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Automatically sets response status to 404
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message)
    {
        super(message);
    }
}
