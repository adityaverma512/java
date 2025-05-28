package com.example.notificationservice.outDTO;

import java.util.Objects;

/**
 * Data Transfer Object representing the response after sending an email.
 * Contains a message indicating the result of the email sending operation.
 */
public class EmailOutDTO {
    /**
     * Response message, typically indicating success or failure status.
     */
    private String message;

    /**
     * Gets the response message.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the response message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailOutDTO that = (EmailOutDTO) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }

    @Override
    public String toString() {
        return "EmailOutDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
