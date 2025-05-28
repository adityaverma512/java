package com.example.orderservice.outDTO;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for exposing order details in responses.
 * Contains information about the order including ID, customer email,
 * product, quantity, order date, and status.
 */
public class OrderOutDTO {
    /**
     * Unique identifier of the order.
     */
    private Long id;

    /**
     * Email address of the customer who placed the order.
     */
    private String customerEmail;

    /**
     * Name of the product ordered.
     */
    private String product;

    /**
     * Quantity of the product ordered.
     */
    private Integer quantity;

    /**
     * Date and time when the order was placed.
     */
    private LocalDateTime orderDate;

    /**
     * Current status of the order.
     */
    private String status;

    /**
     * Gets the unique identifier of the order.
     *
     * @return the order ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the order.
     *
     * @param id the order ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the customer email address.
     *
     * @return the customer email
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the customer email address.
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

    /**
     * Gets the order date and time.
     *
     * @return the orderDate
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the order date and time.
     *
     * @param orderDate the order date to set
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the current status of the order.
     *
     * @return the order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the order.
     *
     * @param status the order status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the OrderOutDTO.
     * Includes product, quantity, orderDate, and status fields.
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return " product='" + product + '\'' +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'';
    }
}
