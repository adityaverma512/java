package com.example.java_training_capstone.mapper;

import com.example.java_training_capstone.entity.Product;
import com.example.java_training_capstone.inDTO.ProductInDTO;
import com.example.java_training_capstone.inDTO.UpdateProductInDTO;
import com.example.java_training_capstone.outDTO.ProductOutDTO;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Mapper class for converting between Product entities and DTOs.
 * This utility class provides methods to convert ProductInDTO to Product,
 * update a Product entity using UpdateProductInDTO, and convert Product to ProductOutDTO.
 */
public class ProductMapper {

    /**
     * Converts a ProductInDTO object to a Product entity.
     * A new UUID is generated for the product, and the creation and update timestamps are set to the current time.
     *
     * @param inDTO the input ProductInDTO containing the product creation data
     * @return a new Product entity populated with data from the DTO
     */
    public static Product toEntity(ProductInDTO inDTO) {
        return new Product(
                UUID.randomUUID().toString(),
                inDTO.getName(),
                inDTO.getDescription(),
                inDTO.getPrice(),
                inDTO.getQuantity(),
                inDTO.getCategory(),
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    /**
     * Converts a Product entity to a ProductOutDTO object.
     * This method is used to send product data in responses.
     *
     * @param product the Product entity to be converted
     * @return a ProductOutDTO containing the relevant product data
     */
    public static ProductOutDTO toOutDTO(Product product) {
        return new ProductOutDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory()
        );
    }

    /**
     * Updates the fields of an existing Product entity using data from an UpdateProductInDTO object.
     * Also updates the `updatedAt` timestamp to the current time.
     *
     * @param product the existing Product entity to be updated
     * @param inDTO   the UpdateProductInDTO containing the new product data
     */
    public static void updateEntity(Product product, UpdateProductInDTO inDTO) {
        product.setName(inDTO.getName());
        product.setDescription(inDTO.getDescription());
        product.setPrice(inDTO.getPrice());
        product.setQuantity(inDTO.getQuantity());
        product.setCategory(inDTO.getCategory());
        product.setUpdatedAt(LocalDateTime.now());
    }

}
