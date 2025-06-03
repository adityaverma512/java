package com.example.java_training_capstone_order_service.outDTO;

import java.util.Objects;

/**
 * Generic wrapper class for standardizing API response format across the application.
 * This class provides a consistent structure for all outbound API responses, ensuring
 * uniformity in how success and error responses are formatted.
 *
 * <p>The response structure includes a status indicator, descriptive message, and
 * generic data payload that can accommodate any type of response data. This approach
 * facilitates consistent error handling and response parsing on the client side.</p>
 *
 * <p>Common usage patterns include:</p>
 * <ul>
 * <li>Success responses with data payload</li>
 * <li>Error responses with error details</li>
 * <li>Status responses with confirmation messages</li>
 * </ul>
 *
 * @param <T> the type of data contained in the response payload
 * @author Generated
 * @version 1.0
 * @since 1.0
 */
public class StandardResponseOutDTO<T> {

    /** Status of the response (e.g., "SUCCESS", "ERROR", "FAILURE") */
    private String status;

    /** Descriptive message providing additional context about the response */
    private String message;

    /** Generic data payload containing the actual response data */
    private T data;

    /**
     * Default constructor for creating an empty StandardResponseOutDTO instance.
     * This constructor is typically used by frameworks for object creation and deserialization.
     */
    public StandardResponseOutDTO() {
    }

    /**
     * Parameterized constructor to create a StandardResponseOutDTO with all fields.
     *
     * @param status the status of the response
     * @param message the descriptive message for the response
     * @param data the data payload of the response
     */
    public StandardResponseOutDTO(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Factory method to create a success response with data and message.
     * This is a convenience method that automatically sets the status to "SUCCESS"
     * and provides a clean way to create successful response objects.
     *
     * @param <T> the type of data in the response
     * @param data the data payload for the successful response
     * @param message the success message to include
     * @return a new StandardResponseOutDTO instance with SUCCESS status
     */
    public static <T> StandardResponseOutDTO<T> success(T data, String message) {
        return new StandardResponseOutDTO<>("SUCCESS", message, data);
    }

    /**
     * Gets the response status.
     *
     * @return the status of the response (e.g., "SUCCESS", "ERROR", "FAILURE")
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the response status.
     *
     * @param status the status of the response
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the response message.
     *
     * @return the descriptive message providing context about the response
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the descriptive message for the response
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the response data payload.
     *
     * @return the generic data payload containing the actual response data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the response data payload.
     *
     * @param data the data payload for the response
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Compares this StandardResponseOutDTO with another object for equality.
     * Two StandardResponseOutDTO objects are considered equal if their status,
     * message, and data fields are all equal.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardResponseOutDTO<?> that = (StandardResponseOutDTO<?>) o;
        return Objects.equals(status, that.status) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    /**
     * Generates a hash code for this StandardResponseOutDTO.
     * The hash code is computed based on the status, message, and data fields.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, message, data);
    }

    /**
     * Returns a string representation of this StandardResponseOutDTO.
     * The string includes all field values in a readable format for debugging and logging purposes.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "StandardResponseOutDTO{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}