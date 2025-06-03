package com.example.java_training_capstone_order_service.outDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object representing order information for outbound operations.
 * This class encapsulates all order-related data that is sent to clients or external systems.
 *
 * <p>The DTO includes comprehensive order details such as product information, pricing,
 * customer details, shipping information, and audit timestamps. It serves as the primary
 * data structure for order responses in REST APIs and inter-service communication.</p>
 *
 * <p>All monetary values are represented using {@link BigDecimal} to ensure precision
 * in financial calculations. Timestamps are formatted consistently for JSON serialization.</p>
 *
 */
public class OrderOutDTO {

    /** Unique identifier for the order */
    private String id;

    /** Unique identifier for the product in the order */
    private String productId;

    /** Name/title of the product being ordered */
    private String productName;

    /** Quantity of the product ordered */
    private Integer quantity;

    /** Price per unit of the product */
    private BigDecimal unitPrice;

    /** Total amount for the order (typically quantity × unitPrice) */
    private BigDecimal totalAmount;

    /** Email address of the customer who placed the order */
    private String customerEmail;

    /** Current status of the order (e.g., PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED) */
    private String status;

    /** Shipping address where the order will be delivered */
    private String shippingAddress;

    /** Flag indicating whether the order is active (true) or inactive/cancelled (false) */
    private Boolean isActive;

    /**
     * Timestamp when the order was created, formatted as yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the order was last updated, formatted as yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * Default constructor for creating an empty OrderOutDTO instance.
     * This constructor is typically used by frameworks for object creation and deserialization.
     */
    public OrderOutDTO() {}

    /**
     * Parameterized constructor to create an OrderOutDTO with all required fields.
     *
     * @param id the unique identifier for the order
     * @param productId the unique identifier for the product
     * @param productName the name of the product
     * @param quantity the quantity of the product ordered
     * @param unitPrice the price per unit of the product
     * @param totalAmount the total amount for the order
     * @param customerEmail the email address of the customer
     * @param status the current status of the order
     * @param shippingAddress the shipping address for delivery
     * @param isActive whether the order is active or inactive
     * @param createdAt the timestamp when the order was created
     * @param updatedAt the timestamp when the order was last updated
     */
    public OrderOutDTO(String id, String productId, String productName, Integer quantity,
                       BigDecimal unitPrice, BigDecimal totalAmount,
                       String customerEmail, String status, String shippingAddress,
                       Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.customerEmail = customerEmail;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the order identifier.
     *
     * @return the unique identifier for the order
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the order identifier.
     *
     * @param id the unique identifier for the order
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the product identifier.
     *
     * @return the unique identifier for the product
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the product identifier.
     *
     * @param productId the unique identifier for the product
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Gets the product name.
     *
     * @return the name of the product being ordered
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     *
     * @param productName the name of the product
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the quantity of the product ordered.
     *
     * @return the quantity of the product
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product ordered.
     *
     * @param quantity the quantity of the product
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the unit price of the product.
     *
     * @return the price per unit of the product
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the product.
     *
     * @param unitPrice the price per unit of the product
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the total amount for the order.
     *
     * @return the total amount for the order
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount for the order.
     *
     * @param totalAmount the total amount for the order
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets the customer's email address.
     *
     * @return the email address of the customer who placed the order
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the customer's email address.
     *
     * @param customerEmail the email address of the customer
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * Gets the current status of the order.
     *
     * @return the current status of the order
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the order.
     *
     * @param status the current status of the order
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the shipping address for the order.
     *
     * @return the shipping address where the order will be delivered
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the shipping address for the order.
     *
     * @param shippingAddress the shipping address for delivery
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Gets the active status of the order.
     *
     * @return true if the order is active, false if inactive or cancelled
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the active status of the order.
     *
     * @param isActive true to mark the order as active, false for inactive
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the timestamp when the order was created.
     *
     * @return the creation timestamp of the order
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the order was created.
     *
     * @param createdAt the creation timestamp of the order
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp when the order was last updated.
     *
     * @return the last update timestamp of the order
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp when the order was last updated.
     *
     * @param updatedAt the last update timestamp of the order
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Compares this OrderOutDTO with another object for equality.
     * Two OrderOutDTO objects are considered equal if all their fields are equal.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderOutDTO that = (OrderOutDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(productId, that.productId) && Objects.equals(productName, that.productName) && Objects.equals(quantity, that.quantity) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(customerEmail, that.customerEmail) && Objects.equals(status, that.status) && Objects.equals(shippingAddress, that.shippingAddress) && Objects.equals(isActive, that.isActive) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    /**
     * Generates a hash code for this OrderOutDTO.
     * The hash code is computed based on all fields of the object.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, productId, productName, quantity, unitPrice, totalAmount, customerEmail, status, shippingAddress, isActive, createdAt, updatedAt);
    }

    /**
     * Returns a string representation of this OrderOutDTO.
     * The string includes all field values in a readable format for debugging and logging purposes.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "OrderOutDTO{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalAmount=" + totalAmount +
                ", customerEmail='" + customerEmail + '\'' +
                ", status='" + status + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}