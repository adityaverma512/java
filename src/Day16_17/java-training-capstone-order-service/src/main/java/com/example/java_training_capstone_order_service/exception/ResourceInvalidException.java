package com.example.java_training_capstone_order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a provided resource or input is invalid or malformed.
 * <p>
 * This exception results in a {@code 400 BAD REQUEST} HTTP status code being returned to the client.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceInvalidException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceInvalidException} with the specified detail message.
     *
     * @param message the detail message explaining why the resource is invalid
     */
    public ResourceInvalidException(String message) {
        super(message);
    }
}
