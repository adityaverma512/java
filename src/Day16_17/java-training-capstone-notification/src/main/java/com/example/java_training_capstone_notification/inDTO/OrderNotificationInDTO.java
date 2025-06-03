package com.example.java_training_capstone_notification.inDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for order notification input data.
 * This class represents incoming order notification data that contains
 * comprehensive information about an order event including product details,
 * customer information, and event metadata.
 *
 * <p>This DTO is typically used for:
 * <ul>
 *   <li>Receiving order event notifications from external systems</li>
 *   <li>Processing order status changes and updates</li>
 *   <li>Triggering email notifications based on order events</li>
 *   <li>Capturing order lifecycle events for audit purposes</li>
 * </ul>
 *
 * <p>The timestamp field uses JSON formatting with pattern "yyyy-MM-dd HH:mm:ss"
 * for consistent date serialization/deserialization.
 */
public class OrderNotificationInDTO {

    /**
     * Unique identifier for the order.
     * This should be a unique string that identifies the order across the system.
     */
    private String orderId;

    /**
     * Unique identifier for the product in the order.
     * This corresponds to the product catalog identifier.
     */
    private String productId;

    /**
     * Human-readable name of the product.
     * This is displayed to customers in notifications and communications.
     */
    private String productName;

    /**
     * Quantity of the product ordered.
     * Must be a positive integer representing the number of units.
     */
    private Integer quantity;

    /**
     * Price per unit of the product.
     * Uses BigDecimal for precise monetary calculations.
     */
    private BigDecimal unitPrice;

    /**
     * Total amount for this order line item.
     * Typically calculated as quantity × unitPrice, but may include adjustments.
     * Uses BigDecimal for precise monetary calculations.
     */
    private BigDecimal totalAmount;

    /**
     * Email address of the customer who placed the order.
     * Used for sending order notifications and updates.
     */
    private String customerEmail;

    /**
     * Current status of the order.
     * Examples: "PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"
     */
    private String status;

    /**
     * Complete shipping address for the order.
     * Contains the full address where the order should be delivered.
     */
    private String shippingAddress;

    /**
     * Type of event that triggered this notification.
     * Examples: "ORDER_CREATED", "ORDER_UPDATED", "ORDER_SHIPPED", "ORDER_CANCELLED"
     */
    private String eventType;

    /**
     * Timestamp when the order event occurred.
     * Formatted as "yyyy-MM-dd HH:mm:ss" for JSON serialization.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Default no-argument constructor.
     * Required for JSON deserialization and framework compatibility.
     */
    public OrderNotificationInDTO() {}

    /**
     * Full constructor with all parameters.
     * Creates a new OrderNotificationInDTO with all fields initialized.
     *
     * @param orderId unique identifier for the order
     * @param productId unique identifier for the product
     * @param productName human-readable name of the product
     * @param quantity number of units ordered
     * @param unitPrice price per unit of the product
     * @param totalAmount total amount for this order line
     * @param customerEmail email address of the customer
     * @param status current status of the order
     * @param shippingAddress complete shipping address
     * @param eventType type of event that triggered this notification
     * @param timestamp when the order event occurred
     */
    public OrderNotificationInDTO(String orderId, String productId, String productName,
                                  Integer quantity, BigDecimal unitPrice, BigDecimal totalAmount,
                                  String customerEmail, String status, String shippingAddress,
                                  String eventType, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.customerEmail = customerEmail;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    /**
     * Gets the unique order identifier.
     *
     * @return the order ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique order identifier.
     *
     * @param orderId the order ID to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the unique product identifier.
     *
     * @return the product ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the unique product identifier.
     *
     * @param productId the product ID to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Gets the human-readable product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the human-readable product name.
     *
     * @param productName the product name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the quantity of products ordered.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of products ordered.
     *
     * @param quantity the quantity to set (should be positive)
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the unit price of the product.
     *
     * @return the unit price
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the product.
     *
     * @param unitPrice the unit price to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the total amount for this order line.
     *
     * @return the total amount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount for this order line.
     *
     * @param totalAmount the total amount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
     * Gets the current order status.
     *
     * @return the order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current order status.
     *
     * @param status the order status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the shipping address for the order.
     *
     * @return the shipping address
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the shipping address for the order.
     *
     * @param shippingAddress the shipping address to set
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Gets the event type that triggered this notification.
     *
     * @return the event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the event type that triggered this notification.
     *
     * @param eventType the event type to set
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the timestamp when the order event occurred.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the order event occurred.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Compares this OrderNotificationInDTO with another object for equality.
     * Two OrderNotificationInDTO objects are considered equal if all their fields are equal.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNotificationInDTO that = (OrderNotificationInDTO) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(unitPrice, that.unitPrice) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(customerEmail, that.customerEmail) &&
                Objects.equals(status, that.status) &&
                Objects.equals(shippingAddress, that.shippingAddress) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(timestamp, that.timestamp);
    }

    /**
     * Generates a hash code for this OrderNotificationInDTO.
     * The hash code is computed using all fields of the object.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, productName, quantity, unitPrice,
                totalAmount, customerEmail, status, shippingAddress,
                eventType, timestamp);
    }

    /**
     * Returns a string representation of this OrderNotificationInDTO.
     * The string includes all field values in a readable format.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "OrderNotificationInDTO{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalAmount=" + totalAmount +
                ", customerEmail='" + customerEmail + '\'' +
                ", status='" + status + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}