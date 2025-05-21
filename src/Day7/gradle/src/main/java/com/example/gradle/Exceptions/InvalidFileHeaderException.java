package com.example.gradle.Exceptions;

public class InvalidFileHeaderException extends RuntimeException {
    public InvalidFileHeaderException(String message) {
        super(message);
    }
}

