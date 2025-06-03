package com.example.java_training_capstone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an attempt is made to create a resource that already exists.
 * <p>
 * This exception results in a {@code 409 CONFLICT} HTTP status code being returned to the client.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code ResourceAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ResourceAlreadyExistsException} with a formatted message
     * including the resource name, the field that caused the conflict, and its value.
     *
     * @param resourceName the name of the resource
     * @param fieldName    the name of the field that caused the conflict
     * @param fieldValue   the value of the conflicting field
     */
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
