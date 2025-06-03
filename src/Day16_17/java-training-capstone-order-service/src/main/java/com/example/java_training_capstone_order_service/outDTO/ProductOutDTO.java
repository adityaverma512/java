package com.example.java_training_capstone_order_service.outDTO;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Data Transfer Object representing product information for outbound operations.
 * This class encapsulates product data that is sent to clients or external systems
 * in response to product-related queries and operations.
 *
 * <p>The DTO contains essential product information including identification, descriptive
 * details, pricing, inventory quantity, and categorization. It serves as the primary
 * data structure for product responses in REST APIs and inter-service communication.</p>
 *
 * <p>Product pricing is represented using {@link BigDecimal} to ensure precision
 * in financial calculations and avoid floating-point arithmetic issues.</p>
 */
public class ProductOutDTO {

    /** Unique identifier for the product */
    private String id;

    /** Name or title of the product */
    private String name;

    /** Detailed description of the product including features and specifications */
    private String description;

    /** Price of the product per unit */
    private BigDecimal price;

    /** Available quantity/stock level of the product */
    private Integer quantity;

    /** Category or classification that the product belongs to */
    private String category;

    /**
     * Gets the product identifier.
     *
     * @return the unique identifier for the product
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the product identifier.
     *
     * @param id the unique identifier for the product
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the product name.
     *
     * @return the name or title of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the name or title of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description.
     *
     * @return the detailed description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the detailed description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product price.
     *
     * @return the price of the product per unit
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the price of the product per unit
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the available quantity of the product.
     *
     * @return the available stock quantity of the product
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the available quantity of the product.
     *
     * @param quantity the available stock quantity of the product
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the product category.
     *
     * @return the category or classification that the product belongs to
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the category or classification that the product belongs to
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Parameterized constructor to create a ProductOutDTO with all required fields.
     *
     * @param id the unique identifier for the product
     * @param name the name or title of the product
     * @param description the detailed description of the product
     * @param price the price of the product per unit
     * @param quantity the available stock quantity of the product
     * @param category the category or classification that the product belongs to
     */
    public ProductOutDTO(String id, String name, String description, BigDecimal price, Integer quantity, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    /**
     * Compares this ProductOutDTO with another object for equality.
     * Two ProductOutDTO objects are considered equal if all their fields are equal.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOutDTO that = (ProductOutDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity) && Objects.equals(category, that.category);
    }

    /**
     * Generates a hash code for this ProductOutDTO.
     * The hash code is computed based on all fields of the object.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, quantity, category);
    }

    /**
     * Returns a string representation of this ProductOutDTO.
     * The string includes all field values in a readable format for debugging and logging purposes.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "ProductOutDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                '}';
    }
}