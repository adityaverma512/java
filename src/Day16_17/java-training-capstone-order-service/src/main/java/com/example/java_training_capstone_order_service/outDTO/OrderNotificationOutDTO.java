package com.example.java_training_capstone_order_service.outDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object for order notifications.
 * This class represents the structure of order notification data that is sent
 * to external systems or services when order events occur.
 *
 * <p>The DTO includes comprehensive order information along with event metadata
 * such as event type and timestamp for tracking and notification purposes.</p>
 *
 */
public class OrderNotificationOutDTO {

    /** Unique identifier for the order */
    private String orderId;

    /** Unique identifier for the product in the order */
    private String productId;

    /** Name/title of the product */
    private String productName;

    /** Quantity of the product ordered */
    private Integer quantity;

    /** Price per unit of the product */
    private BigDecimal unitPrice;

    /** Total amount for the order (quantity × unitPrice) */
    private BigDecimal totalAmount;

    /** Email address of the customer who placed the order */
    private String customerEmail;

    /** Current status of the order (e.g., PENDING, CONFIRMED, SHIPPED, DELIVERED) */
    private String status;

    /** Shipping address for order delivery */
    private String shippingAddress;

    /** Type of event that triggered this notification (e.g., ORDER_CREATED, ORDER_UPDATED) */
    private String eventType;

    /**
     * Timestamp when the notification was created, formatted as yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Default constructor that initializes the timestamp to the current date and time.
     */
    public OrderNotificationOutDTO() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Parameterized constructor to create an OrderNotificationOutDTO with all required fields.
     * The timestamp is automatically set to the current date and time.
     *
     * @param orderId the unique identifier for the order
     * @param productId the unique identifier for the product
     * @param productName the name of the product
     * @param quantity the quantity of the product ordered
     * @param unitPrice the price per unit of the product
     * @param totalAmount the total amount for the order
     * @param customerEmail the email address of the customer
     * @param status the current status of the order
     * @param shippingAddress the shipping address for delivery
     * @param eventType the type of event that triggered this notification
     */
    public OrderNotificationOutDTO(String orderId, String productId, String productName,
                                   Integer quantity, BigDecimal unitPrice, BigDecimal totalAmount,
                                   String customerEmail, String status, String shippingAddress,
                                   String eventType) {
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
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Factory method to create an OrderNotificationOutDTO from an existing OrderOutDTO.
     * This method is useful for converting order data to notification format.
     *
     * @param orderOutDTO the source OrderOutDTO object containing order data
     * @param eventType the type of event that triggered this notification
     * @return a new OrderNotificationOutDTO instance populated with data from the OrderOutDTO
     */
    public static OrderNotificationOutDTO fromOrderOutDTO(OrderOutDTO orderOutDTO, String eventType) {
        return new OrderNotificationOutDTO(
                orderOutDTO.getId(),
                orderOutDTO.getProductId(),
                orderOutDTO.getProductName(),
                orderOutDTO.getQuantity(),
                orderOutDTO.getUnitPrice(),
                orderOutDTO.getTotalAmount(),
                orderOutDTO.getCustomerEmail(),
                orderOutDTO.getStatus(),
                orderOutDTO.getShippingAddress(),
                eventType
        );
    }

    /**
     * Gets the order identifier.
     *
     * @return the unique identifier for the order
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the order identifier.
     *
     * @param orderId the unique identifier for the order
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     * @return the name of the product
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
     * @return the total amount (typically quantity × unitPrice)
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
     * @return the current status (e.g., PENDING, CONFIRMED, SHIPPED, DELIVERED)
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
     * Gets the event type that triggered this notification.
     *
     * @return the type of event (e.g., ORDER_CREATED, ORDER_UPDATED, ORDER_CANCELLED)
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the event type that triggered this notification.
     *
     * @param eventType the type of event
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the timestamp when this notification was created.
     *
     * @return the timestamp of notification creation
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp for this notification.
     *
     * @param timestamp the timestamp of notification creation
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Compares this OrderNotificationOutDTO with another object for equality.
     * Two OrderNotificationOutDTO objects are considered equal if all their fields are equal.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNotificationOutDTO that = (OrderNotificationOutDTO) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) && Objects.equals(productName, that.productName) && Objects.equals(quantity, that.quantity) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(customerEmail, that.customerEmail) && Objects.equals(status, that.status) && Objects.equals(shippingAddress, that.shippingAddress) && Objects.equals(eventType, that.eventType) && Objects.equals(timestamp, that.timestamp);
    }

    /**
     * Generates a hash code for this OrderNotificationOutDTO.
     * The hash code is computed based on all fields of the object.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, productName, quantity, unitPrice, totalAmount, customerEmail, status, shippingAddress, eventType, timestamp);
    }

    /**
     * Returns a string representation of this OrderNotificationOutDTO.
     * The string includes all field values in a readable format.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "OrderNotificationDTO{" +
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