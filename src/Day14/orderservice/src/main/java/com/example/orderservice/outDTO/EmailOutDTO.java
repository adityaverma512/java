package com.example.orderservice.outDTO;

/**
 * Data Transfer Object representing the response from the email notification service.
 * Contains a message indicating the result of the email sending operation.
 */
public class EmailOutDTO {

    /**
     * Message describing the result of the email operation.
     */
    private String message;

    /**
     * Gets the response message.
     *
     * @return the message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the message string to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns a string representation of the EmailOutDTO object.
     *
     * @return a string containing the message field value
     */
    @Override
    public String toString() {
        return "EmailOutDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
