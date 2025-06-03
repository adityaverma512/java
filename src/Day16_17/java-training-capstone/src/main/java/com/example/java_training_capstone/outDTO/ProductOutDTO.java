package com.example.java_training_capstone.outDTO;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for sending product information to clients.
 * <p>
 * This class encapsulates the data to be exposed in API responses, such as product
 * ID, name, description, price, quantity, and category.
 */
public class ProductOutDTO {

    /** The unique identifier of the product. */
    private String id;

    /** The name of the product. */
    private String name;

    /** The description of the product. */
    private String description;

    /** The price of the product. */
    private BigDecimal price;

    /** The available quantity of the product. */
    private Integer quantity;

    /** The category to which the product belongs. */
    private String category;

    /**
     * Constructs a new ProductOutDTO with all fields.
     *
     * @param id          the unique product ID
     * @param name        the product name
     * @param description the product description
     * @param price       the product price
     * @param quantity    the product quantity
     * @param category    the product category
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
     * Returns the product ID.
     *
     * @return the product ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the product ID.
     *
     * @param id the product ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the product price.
     *
     * @return the product price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the product price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns the product quantity.
     *
     * @return the product quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the product quantity.
     *
     * @param quantity the product quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the product category.
     *
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the product category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the object to compare with
     * @return true if this object is the same as the object argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOutDTO that = (ProductOutDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(category, that.category);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, quantity, category);
    }

    /**
     * Returns a string representation of the ProductOutDTO.
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
