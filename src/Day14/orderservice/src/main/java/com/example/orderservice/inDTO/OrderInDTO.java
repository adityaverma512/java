package com.example.orderservice.inDTO;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating a new order.
 * Contains customer email, product name, and quantity with validation constraints.
 */
public class OrderInDTO {

    /**
     * Customer's email address.
     * Must be a non-blank valid Gmail address (e.g. user@gmail.com).
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only Gmail addresses are accepted")
    private String customerEmail;

    /**
     * Product name.
     * Must be a non-blank string containing letters, spaces, hyphens, or apostrophes,
     * between 2 and 30 characters long.
     */
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "First name should be valid")
    private String product;

    /**
     * Quantity of the product ordered.
     * Must be a non-null integer with a minimum value of 1.
     */
    @NotNull
    @Min(value = 1, message = "Valid quantity is required")
    private Integer quantity;

    /**
     * Gets the customer's email address.
     *
     * @return the customer email
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the customer's email address.
     *
     * @param customerEmail the customer email to set
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the product name.
     *
     * @param product the product name to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Gets the quantity ordered.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity ordered.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
