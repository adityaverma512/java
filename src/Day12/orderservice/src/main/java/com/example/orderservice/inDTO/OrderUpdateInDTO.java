package com.example.orderservice.inDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object for updating the status of an existing order.
 * Contains validation to ensure the status is not blank and matches allowed values.
 */
public class OrderUpdateInDTO {

    /**
     * Status of the order.
     * Must be one of the following values: "Ordered", "Processing", or "Completed".
     */
    @NotBlank(message = "Status must not be blank")
    @Pattern(
            regexp = "^(Ordered|Processing|Completed)$",
            message = "Status must be one of: Ordered, Processing, Completed"
    )
    private String status;

    /**
     * Gets the order status.
     *
     * @return the current order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the order status.
     *
     * @param status the new order status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
