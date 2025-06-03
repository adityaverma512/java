package com.example.java_training_capstone.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * A standard structure for error responses returned by the application.
 * This class is typically used in exception handling to return
 * structured and informative error messages to API clients.
 */
public class ErrorResponse {

    /**
     * HTTP status code associated with the error.
     */
    private int status;

    /**
     * A descriptive message explaining the error.
     */
    private String message;

    /**
     * The timestamp when the error occurred.
     * Formatted as "yyyy-MM-dd HH:mm:ss" in JSON output.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Default constructor that sets the timestamp to the current time.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an ErrorResponse with the given status and message.
     * Automatically sets the timestamp to the current time.
     *
     * @param status  the HTTP status code
     * @param message the error message
     */
    public ErrorResponse(int status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return the status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status code.
     *
     * @param status the status code to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the error message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the timestamp when the error occurred.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
