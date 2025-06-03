package com.example.java_training_capstone_order_service.inDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

/**
 * Data Transfer Object used to update the status of an order.
 * <p>
 * This DTO ensures that the status is not blank and matches one of the allowed values.
 * Allowed status values are: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED.
 * </p>
 */
public class OrderStatusUpdateInDTO {

    /**
     * The new status of the order.
     * <p>
     * Must not be blank and must match one of the predefined values:
     * PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED.
     * </p>
     */
    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "^(PENDING|CONFIRMED|SHIPPED|DELIVERED|CANCELLED)$",
            message = "Status must be one of: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED"
    )
    private String status;

    /**
     * Default no-argument constructor.
     */
    public OrderStatusUpdateInDTO() {}

    /**
     * Constructor with status parameter.
     *
     * @param status the new status to set
     */
    public OrderStatusUpdateInDTO(String status) {
        this.status = status;
    }

    /**
     * Gets the order status.
     *
     * @return the current status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the order status.
     *
     * @param status the new status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Checks if this object is equal to another object.
     *
     * @param o the other object to compare with
     * @return true if both objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatusUpdateInDTO that = (OrderStatusUpdateInDTO) o;
        return Objects.equals(status, that.status);
    }

    /**
     * Returns the hash code of this object.
     *
     * @return hash code based on the status field
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(status);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return string representation including the status
     */
    @Override
    public String toString() {
        return "OrderStatusUpdateDTO{" +
                "status='" + status + '\'' +
                '}';
    }
}
