package com.example.java_training_capstone.entity;

import com.google.cloud.spring.data.spanner.core.mapping.Column;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a product entity mapped to the "product" table in Google Cloud Spanner.
 */
@Table(name = "product")
public class Product {

    /**
     * Unique identifier for the product.
     */
    @PrimaryKey
    @Column(name = "id")
    private String id;

    /**
     * Name of the product.
     */
    @Column(name = "name")
    private String name;

    /**
     * Description of the product.
     */
    @Column(name = "description")
    private String description;

    /**
     * Price of the product.
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * Quantity of the product available in stock.
     */
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * Category of the product.
     */
    @Column(name = "category")
    private String category;

    /**
     * Indicates whether the product is active.
     */
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * Timestamp when the product was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the product was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Constructor with all fields.
     *
     * @param id         the product ID
     * @param name       the product name
     * @param description the product description
     * @param price      the product price
     * @param quantity   the product quantity
     * @param category   the product category
     * @param isActive   whether the product is active
     * @param createdAt  creation timestamp
     * @param updatedAt  last update timestamp
     */
    public Product(String id, String name, String description, BigDecimal price, Integer quantity, String category, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Default constructor.
     */
    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Checks equality based on all fields.
     *
     * @param o the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(price, product.price) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(category, product.category) &&
                Objects.equals(isActive, product.isActive) &&
                Objects.equals(createdAt, product.createdAt) &&
                Objects.equals(updatedAt, product.updatedAt);
    }

    /**
     * Generates hash code based on all fields.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, quantity, category, isActive, createdAt, updatedAt);
    }

    /**
     * Returns a string representation of the Product object.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
