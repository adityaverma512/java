package com.example.java_training_capstone.service;

import com.example.java_training_capstone.inDTO.ProductInDTO;
import com.example.java_training_capstone.inDTO.UpdateProductInDTO;
import com.example.java_training_capstone.outDTO.ProductOutDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * The interface Product service.
 */
public interface ProductService {
    /**
     * Create product product out dto.
     *
     * @param productDto the product dto
     * @return the product out dto
     */
    ProductOutDTO createProduct(ProductInDTO productDto);

    /**
     * Add multiple products from csv list.
     *
     * @param csvFile the csv file
     * @return the list
     */
    List<ProductOutDTO> addMultipleProductsFromCsv(MultipartFile csvFile);

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
    ProductOutDTO getProductById(String id);

    /**
     * Gets all products.
     *
     * @return the all products
     */
    List<ProductOutDTO> getAllProducts();

    /**
     * Export all products to csv byte array input stream.
     *
     * @return the byte array input stream
     */
    ByteArrayInputStream exportAllProductsToCsv();

    /**
     * Read products from csv list.
     *
     * @param csvFile the csv file
     * @return the list
     */
    List<ProductOutDTO> readProductsFromCsv(MultipartFile csvFile);

    /**
     * Gets product by name.
     *
     * @param Name the name
     * @return the product by name
     */
    ProductOutDTO getProductByName(String Name);

    /**
     * Gets products by category.
     *
     * @param category the category
     * @return the products by category
     */
    List<ProductOutDTO> getProductsByCategory(String category);

    /**
     * Update product by id product out dto.
     *
     * @param id                 the id
     * @param updateProductInDTO the update product in dto
     * @return the product out dto
     */
    ProductOutDTO updateProductByID(String id, UpdateProductInDTO updateProductInDTO);

    /**
     * Update product by name product out dto.
     *
     * @param name               the name
     * @param updateProductInDTO the update product in dto
     * @return the product out dto
     */
    ProductOutDTO updateProductByName(String name, UpdateProductInDTO updateProductInDTO);

    /**
     * Delete product by id.
     *
     * @param id the id
     */
    void deleteProductByID(String id);

    /**
     * Delete product by name.
     *
     * @param name the name
     */
    void deleteProductByName(String name);
}
