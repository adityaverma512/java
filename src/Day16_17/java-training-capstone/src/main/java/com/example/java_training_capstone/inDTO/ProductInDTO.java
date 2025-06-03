package com.example.java_training_capstone.inDTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Data Transfer Object used for creating a new product.
 * This DTO carries information such as name, description, price, quantity, and category of the product.
 * It includes validation annotations to ensure required and valid data is received.
 */
public class ProductInDTO {

    /**
     * The name of the product.
     * Cannot be null or blank.
     */
    @NotBlank(message = "Product name is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "Product name should be valid")
    private String name;

    /**
     * The description of the product. Optional field.
     */
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "Product description should be valid")
    private String description;

    /**
     * The price of the product.
     * Must be greater than 0.
     */
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    /**
     * The quantity of the product in stock.
     * Must be a non-negative integer.
     */
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;

    /**
     * The category to which the product belongs.
     * Cannot be null or blank.
     */
    @NotBlank(message = "Category is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "Category name should be valid")
    private String category;

    /**
     * All-args constructor to initialize ProductInDTO with values.
     *
     * @param name        the name of the product
     * @param description the description of the product
     * @param price       the price of the product
     * @param quantity    the available quantity
     * @param category    the category of the product
     */
    public ProductInDTO(String name, String description, BigDecimal price, Integer quantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    /**
     * Default constructor.
     */
    public ProductInDTO() {
    }

    /**
     * Gets the product name.
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description.
     *
     * @return product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product price.
     *
     * @return product price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the product quantity.
     *
     * @return product quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the product quantity.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the product category.
     *
     * @return product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Compares this DTO to another object for equality.
     *
     * @param o the other object
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInDTO that = (ProductInDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(category, that.category);
    }

    /**
     * Generates a hash code based on the fields.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, quantity, category);
    }

    /**
     * Returns a string representation of the ProductInDTO.
     *
     * @return string describing the product
     */
    @Override
    public String toString() {
        return "ProductInDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                '}';
    }
}
