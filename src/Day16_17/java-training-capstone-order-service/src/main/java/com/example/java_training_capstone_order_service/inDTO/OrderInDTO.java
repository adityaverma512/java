package com.example.java_training_capstone_order_service.inDTO;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Data Transfer Object for incoming order requests.
 * This class represents the data structure used when creating or updating orders
 * and includes validation constraints to ensure data integrity.
 */
public class OrderInDTO {

    /**
     * The product identifier for the order.
     * Must not be blank or null.
     */
    @NotBlank(message = "Product ID is required")
    private String productId;

    /**
     * The quantity of products being ordered.
     * Must be at least 1 and cannot be null.
     */
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    /**
     * The email address of the customer placing the order.
     * Must be a valid email format and cannot be blank.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only Gmail addresses are accepted")
    private String customerEmail;

    /**
     * The current status of the order.
     * Must be one of: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED.
     * Defaults to "PENDING" if not specified.
     */
    @Pattern(regexp = "^(PENDING|CONFIRMED|SHIPPED|DELIVERED|CANCELLED)$",
            message = "Status must be one of: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED")
    private String status = "PENDING";

    /**
     * The shipping address for the order delivery.
     * Must not be blank or null.
     */
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    /**
     * Default constructor for OrderInDTO.
     * Initializes an empty OrderInDTO instance.
     */
    public OrderInDTO() {}

    /**
     * Parameterized constructor for OrderInDTO.
     *
     * @param productId       the product identifier
     * @param quantity        the quantity of products
     * @param customerEmail   the customer's email address
     * @param status          the order status
     * @param shippingAddress the shipping address
     */
    public OrderInDTO(String productId, Integer quantity, String customerEmail, String status, String shippingAddress) {
        this.productId = productId;
        this.quantity = quantity;
        this.customerEmail = customerEmail;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    /**
     * Gets the product identifier.
     *
     * @return the product ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the product identifier.
     *
     * @param productId the product ID to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Gets the quantity of products.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of products.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

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
     * Gets the order status.
     *
     * @return the order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the order status.
     *
     * @param status the order status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the shipping address.
     *
     * @return the shipping address
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the shipping address.
     *
     * @param shippingAddress the shipping address to set
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderInDTO that = (OrderInDTO) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(customerEmail, that.customerEmail) &&
                Objects.equals(status, that.status) &&
                Objects.equals(shippingAddress, that.shippingAddress);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity, customerEmail, status, shippingAddress);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "OrderInDTO{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", customerEmail='" + customerEmail + '\'' +
                ", status='" + status + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}