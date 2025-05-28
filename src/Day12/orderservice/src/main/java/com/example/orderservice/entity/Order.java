package com.example.orderservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents an Order entity mapped to the "orders" table in the database.
 * Stores information about a customer's product order, including product details,
 * quantity, order date, customer email, and order status.
 */
@Entity
@Table(name = "orders")
public class Order {

    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The email address of the customer placing the order.
     */
    private String customerEmail;

    /**
     * The name of the product ordered.
     */
    private String product;

    /**
     * The quantity of the product ordered.
     */
    private Integer quantity;

    /**
     * The date and time when the order was placed.
     */
    private LocalDateTime orderDate;

    /**
     * The current status of the order (e.g., "PLACED", "SHIPPED", "DELIVERED").
     */
    private String status;

    /**
     * Returns the ID of the order.
     *
     * @return the order ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id the order ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the customer's email.
     *
     * @return the customer email
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the customer's email.
     *
     * @param customerEmail the customer email
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * Returns the name of the product.
     *
     * @return the product name
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the name of the product.
     *
     * @param product the product name
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity the quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the order date.
     *
     * @return the date and time the order was placed
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the order date.
     *
     * @param orderDate the date and time the order was placed
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns the current status of the order.
     *
     * @return the order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status the order status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
