package com.example.java_training_capstone.controller;

import com.example.java_training_capstone.inDTO.ProductInDTO;
import com.example.java_training_capstone.inDTO.UpdateProductInDTO;
import com.example.java_training_capstone.outDTO.ProductOutDTO;
import com.example.java_training_capstone.outDTO.StandardResponseOutDTO;
import com.example.java_training_capstone.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * REST controller for managing product resources.
 * Provides endpoints for CRUD operations and CSV-based import/export functionality.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    /**
     * Constructs the ProductController with the required ProductService dependency.
     *
     * @param productService the service layer handling product business logic
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product.
     *
     * @param productInDTO the product input data
     * @return the created product with a success message
     */
    @PostMapping
    public ResponseEntity<StandardResponseOutDTO<ProductOutDTO>> createProduct(@Valid @RequestBody ProductInDTO productInDTO) {
        logger.info("Received request to create product: {}", productInDTO.getName());

        ProductOutDTO createdProduct = productService.createProduct(productInDTO);
        StandardResponseOutDTO<ProductOutDTO> response = StandardResponseOutDTO.success(createdProduct, "Product created successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the product ID
     * @return the found product
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<StandardResponseOutDTO<ProductOutDTO>> getProductById(@PathVariable String id) {
        logger.info("Received request to get product by id: {}", id);

        ProductOutDTO product = productService.getProductById(id);
        StandardResponseOutDTO<ProductOutDTO> response = StandardResponseOutDTO.success(product, "Product retrieved successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    @GetMapping
    public ResponseEntity<StandardResponseOutDTO<List<ProductOutDTO>>> getAllProducts() {
        logger.info("Received request to get all products");

        List<ProductOutDTO> products = productService.getAllProducts();
        StandardResponseOutDTO<List<ProductOutDTO>> response = StandardResponseOutDTO.success(products, "Products retrieved successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name the product name
     * @return the product found with the given name
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<StandardResponseOutDTO<ProductOutDTO>> getProductsByName(@PathVariable String name) {
        logger.info("Received request to get product by name: {}", name);

        ProductOutDTO products = productService.getProductByName(name);
        StandardResponseOutDTO<ProductOutDTO> response = StandardResponseOutDTO.success(products, "Product retrieved successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all products matching a given category.
     *
     * @param category the product category
     * @return list of products in the specified category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<StandardResponseOutDTO<List<ProductOutDTO>>> getProductsByCategory(@PathVariable String category) {
        logger.info("Received request to get products by category: {}", category);

        List<ProductOutDTO> products = productService.getProductsByCategory(category);
        StandardResponseOutDTO<List<ProductOutDTO>> response = StandardResponseOutDTO.success(products, "Products retrieved successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Updates a product identified by its ID.
     *
     * @param id                 the product ID
     * @param updateProductInDTO the updated product details
     * @return the updated product
     */
    @PutMapping("/id/{id}")
    public ResponseEntity<StandardResponseOutDTO<ProductOutDTO>> updateProduct(@PathVariable String id, @Valid @RequestBody UpdateProductInDTO updateProductInDTO) {
        logger.info("Received request to update product with id: {}", id);

        ProductOutDTO updatedProduct = productService.updateProductByID(id, updateProductInDTO);
        StandardResponseOutDTO<ProductOutDTO> response = StandardResponseOutDTO.success(updatedProduct, "Product updated successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Updates a product identified by its name.
     *
     * @param name               the product name
     * @param updateProductInDTO the updated product details
     * @return the updated product
     */
    @PutMapping("/name/{name}")
    public ResponseEntity<StandardResponseOutDTO<ProductOutDTO>> updateProductByName(@PathVariable String name, @Valid @RequestBody UpdateProductInDTO updateProductInDTO) {
        logger.info("Received request to update product with name: {}", name);

        ProductOutDTO updatedProduct = productService.updateProductByName(name, updateProductInDTO);
        StandardResponseOutDTO<ProductOutDTO> response = StandardResponseOutDTO.success(updatedProduct, "Product updated successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the product ID
     * @return a success message
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<StandardResponseOutDTO<Void>> deleteProduct(@PathVariable String id) {
        logger.info("Received request to delete product with id: {}", id);

        productService.deleteProductByID(id);
        StandardResponseOutDTO<Void> response = StandardResponseOutDTO.success(null, "Product deleted successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a product by its name.
     *
     * @param name the product name
     * @return a success message
     */
    @DeleteMapping("/name/{name}")
    public ResponseEntity<StandardResponseOutDTO<Void>> deleteProductByName(@PathVariable String name) {
        logger.info("Received request to delete product with name: {}", name);

        productService.deleteProductByName(name);
        StandardResponseOutDTO<Void> response = StandardResponseOutDTO.success(null, "Product deleted successfully");

        return ResponseEntity.ok(response);
    }

    /**
     * Uploads a CSV file and creates products in bulk.
     *
     * @param file the CSV file containing product data
     * @return list of created products
     */
    @PostMapping("/upload-csv")
    public ResponseEntity<StandardResponseOutDTO<List<ProductOutDTO>>> uploadProductsFromCsv(
            @RequestParam("file") MultipartFile file) {

        logger.info("Received request to upload products from CSV file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            StandardResponseOutDTO<List<ProductOutDTO>> response =
                    StandardResponseOutDTO.error("CSV file is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            StandardResponseOutDTO<List<ProductOutDTO>> response =
                    StandardResponseOutDTO.error("Only CSV files are allowed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<ProductOutDTO> createdProducts = productService.addMultipleProductsFromCsv(file);
        StandardResponseOutDTO<List<ProductOutDTO>> response =
                StandardResponseOutDTO.success(createdProducts,
                        "Successfully created " + createdProducts.size() + " products from CSV");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Exports all products to a downloadable CSV file.
     *
     * @return CSV file containing all products
     */
    @GetMapping("/export-csv")
    public ResponseEntity<InputStreamResource> exportProductsToCsv() {
        logger.info("Received request to export products to CSV");

        ByteArrayInputStream csvData = productService.exportAllProductsToCsv();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "products_export_" + timestamp + ".csv";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(csvData));
    }

    /**
     * Reads product data from a CSV file without saving them to the database.
     *
     * @param file the CSV file
     * @return list of products read from the file
     */
    @PostMapping("/read-csv")
    public ResponseEntity<StandardResponseOutDTO<List<ProductOutDTO>>> readProductsFromCsv(
            @RequestParam("file") MultipartFile file) {

        logger.info("Received request to read products from CSV file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            StandardResponseOutDTO<List<ProductOutDTO>> response =
                    StandardResponseOutDTO.error("CSV file is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            StandardResponseOutDTO<List<ProductOutDTO>> response =
                    StandardResponseOutDTO.error("Only CSV files are allowed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<ProductOutDTO> products = productService.readProductsFromCsv(file);
        StandardResponseOutDTO<List<ProductOutDTO>> response =
                StandardResponseOutDTO.success(products,
                        "Successfully read " + products.size() + " products from CSV");

        return ResponseEntity.ok(response);
    }
}
