package com.example.java_training_capstone.serviceImpl;

import com.example.java_training_capstone.entity.Product;
import com.example.java_training_capstone.exception.ResourceAlreadyExistsException;
import com.example.java_training_capstone.exception.ResourceInvalidException;
import com.example.java_training_capstone.exception.ResourceNotFoundException;
import com.example.java_training_capstone.inDTO.ProductInDTO;
import com.example.java_training_capstone.inDTO.UpdateProductInDTO;
import com.example.java_training_capstone.mapper.ProductMapper;
import com.example.java_training_capstone.outDTO.ProductOutDTO;
import com.example.java_training_capstone.repository.ProductRepository;
import com.example.java_training_capstone.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductService interface providing comprehensive product management functionality.
 * This service handles CRUD operations, CSV import/export, and various product retrieval methods.
 *
 * <p>Key features include:</p>
 * <ul>
 *   <li>Product creation and validation</li>
 *   <li>Bulk CSV import with error handling</li>
 *   <li>CSV export functionality</li>
 *   <li>Product retrieval by various criteria</li>
 *   <li>Product updates and soft deletion</li>
 * </ul>
 *
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * Constructs a new ProductServiceImpl with the required dependencies.
     *
     * @param productRepository the repository for product data access operations
     * @param validator the bean validator for validating product DTOs
     */
    public ProductServiceImpl(ProductRepository productRepository, Validator validator) {
        this.productRepository = productRepository;
        this.validator = validator;
    }

    /**
     * Creates a new product in the system.
     *
     * @param productInDTO the product data transfer object containing product details
     * @return ProductOutDTO the created product with generated ID and timestamps
     * @throws ResourceAlreadyExistsException if a product with the same name already exists
     * @throws ResourceInvalidException if the product data is invalid
     */
    @Override
    public ProductOutDTO createProduct(ProductInDTO productInDTO) {
        logger.info("Creating product with name: {}", productInDTO.getName());

        if (productRepository.findByNameAndIsActiveTrue(productInDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Product with name '" + productInDTO.getName() + "' already exists");
        }

        Product product = ProductMapper.toEntity(productInDTO);

        Product savedProduct = productRepository.save(product);
        logger.info("Product created successfully with id: {}", savedProduct.getId());
        return ProductMapper.toOutDTO(savedProduct);
    }

    /**
     * Imports multiple products from a CSV file with comprehensive error handling.
     *
     * <p>Expected CSV format: name,description,price,quantity,category</p>
     * <p>The method processes each line individually, collecting errors for invalid entries
     * while still creating valid products. Validation is performed on each product.</p>
     *
     * @param csvFile the multipart file containing CSV data
     * @return List&lt;ProductOutDTO&gt; list of successfully created products
     * @throws ResourceInvalidException if the CSV file is empty, invalid, or contains no valid products
     * @throws ResourceAlreadyExistsException if products with duplicate names are found
     */
    @Override
    public List<ProductOutDTO> addMultipleProductsFromCsv(MultipartFile csvFile) {
        logger.info("Adding multiple products from CSV file: {}", csvFile.getOriginalFilename());

        if (csvFile.isEmpty()) {
            throw new ResourceInvalidException("CSV file is empty");
        }

        List<ProductOutDTO> createdProducts = new ArrayList<>();
        List<String> allErrors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] values = parseCsvLine(line);

                    if (values.length < 5) {
                        allErrors.add("Line " + lineNumber + ": Insufficient columns. Expected format: name,description,price,quantity,category");
                        continue;
                    }

                    String name = values[0].trim();
                    String description = values[1].trim();
                    String priceStr = values[2].trim();
                    String quantityStr = values[3].trim();
                    String category = values[4].trim();

                    BigDecimal price;
                    Integer quantity;

                    try {
                        price = new BigDecimal(priceStr);
                    } catch (NumberFormatException e) {
                        allErrors.add("Line " + lineNumber + ": Invalid price format '" + priceStr + "'");
                        continue;
                    }

                    try {
                        quantity = Integer.parseInt(quantityStr);
                    } catch (NumberFormatException e) {
                        allErrors.add("Line " + lineNumber + ": Invalid quantity format '" + quantityStr + "'");
                        continue;
                    }

                    ProductInDTO productInDTO = new ProductInDTO(name, description, price, quantity, category);

                    Set<ConstraintViolation<ProductInDTO>> violations = validator.validate(productInDTO);
                    if (!violations.isEmpty()) {
                        StringBuilder errorMsg = new StringBuilder("Line " + lineNumber + " validation errors: ");
                        for (ConstraintViolation<ProductInDTO> violation : violations) {
                            errorMsg.append(violation.getMessage()).append("; ");
                        }
                        allErrors.add(errorMsg.toString());
                        continue;
                    }

                    if (productRepository.findByNameAndIsActiveTrue(name).isPresent()) {
                        allErrors.add("Line " + lineNumber + ": Product with name '" + name + "' already exists");
                        continue;
                    }

                    Product product = ProductMapper.toEntity(productInDTO);
                    Product savedProduct = productRepository.save(product);

                    createdProducts.add(ProductMapper.toOutDTO(savedProduct));
                    logger.debug("Successfully created product: {}", name);

                } catch (Exception e) {
                    allErrors.add("Line " + lineNumber + ": Unexpected error - " + e.getMessage());
                }
            }

        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage());
            throw new ResourceInvalidException("Failed to process CSV file");
        }

        if (!allErrors.isEmpty()) {
            String consolidatedErrors = String.join("\n", allErrors);
            if (createdProducts.isEmpty()) {
                throw new ResourceInvalidException("CSV processing failed with errors:\n" + consolidatedErrors);
            } else {
                logger.warn("CSV processing completed with {} products created and {} errors:\n{}",
                        createdProducts.size(), allErrors.size(), consolidatedErrors);
            }
        }

        if (createdProducts.isEmpty()) {
            throw new ResourceInvalidException("No valid products found in CSV file");
        }

        logger.info("Successfully created {} products from CSV", createdProducts.size());
        return createdProducts;
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the unique identifier of the product
     * @return ProductOutDTO the product details
     * @throws ResourceNotFoundException if no active product is found with the given ID
     */
    @Override
    public ProductOutDTO getProductById(String id) {
        logger.info("Fetching product with id: {}", id);

        Product product = productRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        return ProductMapper.toOutDTO(product);
    }

    /**
     * Retrieves all active products from the system.
     *
     * @return List&lt;ProductOutDTO&gt; list of all active products, empty list if no products exist
     */
    @Override
    public List<ProductOutDTO> getAllProducts() {
        logger.info("Fetching all active products");

        List<Product> products = productRepository.findByIsActiveTrue();
        return products.stream()
                .map(ProductMapper::toOutDTO)
                .collect(Collectors.toList());
    }

    /**
     * Exports all active products to a CSV format as a byte stream.
     *
     * <p>CSV format: ID,Name,Description,Price,Quantity,Category,Created At,Updated At</p>
     * <p>Values containing commas, quotes, or newlines are properly escaped.</p>
     *
     * @return ByteArrayInputStream containing the CSV data
     * @throws ResourceNotFoundException if no active products are found to export
     * @throws ResourceInvalidException if an error occurs during CSV generation
     */
    @Override
    public ByteArrayInputStream exportAllProductsToCsv() {
        logger.info("Exporting all products to CSV");

        List<Product> products = productRepository.findByIsActiveTrue();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No active products found to export");
        }

        try {
            StringBuilder csvContent = new StringBuilder();
            csvContent.append("ID,Name,Description,Price,Quantity,Category,Created At,Updated At\n");

            for (Product product : products) {
                csvContent.append(escapeCsvValue(product.getId())).append(",")
                        .append(escapeCsvValue(product.getName())).append(",")
                        .append(escapeCsvValue(product.getDescription())).append(",")
                        .append(product.getPrice()).append(",")
                        .append(product.getQuantity()).append(",")
                        .append(escapeCsvValue(product.getCategory())).append(",")
                        .append(product.getCreatedAt()).append(",")
                        .append(product.getUpdatedAt()).append("\n");
            }

            logger.info("Exported {} products to CSV", products.size());
            return new ByteArrayInputStream(csvContent.toString().getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            logger.error("Error generating CSV export: {}", e.getMessage());
            throw new ResourceInvalidException("Failed to generate CSV export: " + e.getMessage());
        }
    }

    /**
     * Reads and validates products from a CSV file without persisting them to the database.
     *
     * <p>Expected CSV format: ID,Name,Description,Price,Quantity,Category,Created At,Updated At</p>
     * <p>This method is useful for previewing CSV data before import or for data validation.</p>
     *
     * @param csvFile the multipart file containing CSV data
     * @return List&lt;ProductOutDTO&gt; list of valid products from the CSV
     * @throws ResourceInvalidException if the CSV file is empty, invalid, or contains no valid products
     * @throws ResourceNotFoundException if no valid products are found in the CSV
     */
    @Override
    public List<ProductOutDTO> readProductsFromCsv(MultipartFile csvFile) {
        logger.info("Reading products from CSV file: {}", csvFile.getOriginalFilename());

        if (csvFile.isEmpty()) {
            throw new ResourceInvalidException("CSV file is empty");
        }

        List<ProductOutDTO> products = new ArrayList<>();
        List<String> allErrors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] values = parseCsvLine(line);

                    if (values.length < 8) {
                        allErrors.add("Line " + lineNumber + ": Insufficient columns. Expected format: ID,Name,Description,Price,Quantity,Category,Created At,Updated At");
                        continue;
                    }

                    String id = values[0].trim();
                    String name = values[1].trim();
                    String description = values[2].trim();
                    String priceStr = values[3].trim();
                    String quantityStr = values[4].trim();
                    String category = values[5].trim();

                    if (id.isEmpty()) {
                        allErrors.add("Line " + lineNumber + ": Product ID is required");
                        continue;
                    }

                    if (name.isEmpty()) {
                        allErrors.add("Line " + lineNumber + ": Product name is required");
                        continue;
                    }

                    BigDecimal price;
                    Integer quantity;

                    try {
                        price = new BigDecimal(priceStr);
                        if (price.compareTo(BigDecimal.ZERO) <= 0) {
                            allErrors.add("Line " + lineNumber + ": Price must be greater than 0");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        allErrors.add("Line " + lineNumber + ": Invalid price format '" + priceStr + "'");
                        continue;
                    }

                    try {
                        quantity = Integer.parseInt(quantityStr);
                        if (quantity < 0) {
                            allErrors.add("Line " + lineNumber + ": Quantity must be non-negative");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        allErrors.add("Line " + lineNumber + ": Invalid quantity format '" + quantityStr + "'");
                        continue;
                    }

                    if (category.isEmpty()) {
                        allErrors.add("Line " + lineNumber + ": Category is required");
                        continue;
                    }

                    ProductOutDTO productOutDTO = new ProductOutDTO(id, name, description, price, quantity, category);
                    products.add(productOutDTO);

                } catch (Exception e) {
                    allErrors.add("Line " + lineNumber + ": Unexpected error - " + e.getMessage());
                }
            }

        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage());
            throw new ResourceInvalidException("Failed to process CSV file: " + e.getMessage());
        }

        if (!allErrors.isEmpty()) {
            String consolidatedErrors = String.join("\n", allErrors);
            if (products.isEmpty()) {
                throw new ResourceInvalidException("CSV reading failed with errors:\n" + consolidatedErrors);
            } else {
                logger.warn("CSV reading completed with {} products read and {} errors:\n{}",
                        products.size(), allErrors.size(), consolidatedErrors);
            }
        }

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No valid products found in CSV file");
        }

        logger.info("Successfully read {} products from CSV", products.size());
        return products;
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name the name of the product to retrieve
     * @return ProductOutDTO the product details
     * @throws ResourceNotFoundException if no active product is found with the given name
     */
    @Override
    public ProductOutDTO getProductByName(String name) {
        logger.info("Fetching product with name: {}", name);

        Product product = productRepository.findByNameAndIsActiveTrue(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name: " + name));

        return ProductMapper.toOutDTO(product);
    }

    /**
     * Retrieves all products belonging to a specific category.
     *
     * @param category the category to filter products by
     * @return List&lt;ProductOutDTO&gt; list of products in the specified category, empty list if none found
     */
    @Override
    public List<ProductOutDTO> getProductsByCategory(String category) {
        logger.info("Fetching products by category: {}", category);

        List<Product> products = productRepository.findByCategoryAndIsActiveTrue(category);
        return products.stream()
                .map(ProductMapper::toOutDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing product identified by its ID.
     *
     * @param id the unique identifier of the product to update
     * @param updateProductInDTO the updated product information
     * @return ProductOutDTO the updated product details
     * @throws ResourceNotFoundException if no active product is found with the given ID
     * @throws ResourceAlreadyExistsException if another product with the new name already exists
     * @throws ResourceInvalidException if the updated price is not greater than zero
     */
    @Override
    public ProductOutDTO updateProductByID(String id, UpdateProductInDTO updateProductInDTO) {
        logger.info("Updating product with id: {}", id);

        Product existingProduct = productRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productRepository.findByNameAndIsActiveTrue(updateProductInDTO.getName())
                .ifPresent(product -> {
                    if (!product.getId().equals(id)) {
                        throw new ResourceAlreadyExistsException("Product with name '" + updateProductInDTO.getName() + "' already exists");
                    }
                });

        if (updateProductInDTO.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResourceInvalidException("Product price must be greater than 0");
        }

        ProductMapper.updateEntity(existingProduct, updateProductInDTO);

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated successfully with id: {}", updatedProduct.getId());

        return ProductMapper.toOutDTO(updatedProduct);
    }

    /**
     * Updates an existing product identified by its name.
     *
     * @param name the name of the product to update
     * @param updateProductInDTO the updated product information
     * @return ProductOutDTO the updated product details
     * @throws ResourceNotFoundException if no active product is found with the given name
     * @throws ResourceAlreadyExistsException if another product with the new name already exists
     * @throws ResourceInvalidException if the updated price is not greater than zero
     */
    @Override
    public ProductOutDTO updateProductByName(String name, UpdateProductInDTO updateProductInDTO) {
        logger.info("Updating product with name: {}", name);

        Product existingProduct = productRepository.findByNameAndIsActiveTrue(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name: " + name));

        productRepository.findByNameAndIsActiveTrue(updateProductInDTO.getName())
                .ifPresent(product -> {
                    if (!product.getName().equals(name)) {
                        throw new ResourceAlreadyExistsException("Product with name '" + updateProductInDTO.getName() + "' already exists");
                    }
                });

        if (updateProductInDTO.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new ResourceInvalidException("Product price must be greater than 0");
        }

        ProductMapper.updateEntity(existingProduct, updateProductInDTO);

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated successfully with name: {}", updatedProduct.getName());

        return ProductMapper.toOutDTO(updatedProduct);
    }

    /**
     * Performs a soft delete on a product by its ID.
     *
     * <p>The product is marked as inactive rather than being physically deleted from the database.
     * This preserves data integrity and allows for potential recovery.</p>
     *
     * @param id the unique identifier of the product to delete
     * @throws ResourceNotFoundException if no active product is found with the given ID
     */
    @Override
    public void deleteProductByID(String id) {
        logger.info("Deleting product with id: {}", id);

        Product product = productRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setActive(false);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        logger.info("Product deleted successfully with id: {}", id);
    }

    /**
     * Performs a soft delete on a product by its name.
     *
     * <p>The product is marked as inactive rather than being physically deleted from the database.
     * This preserves data integrity and allows for potential recovery.</p>
     *
     * @param name the name of the product to delete
     * @throws ResourceNotFoundException if no active product is found with the given name
     */
    @Override
    public void deleteProductByName(String name) {
        logger.info("Deleting product with name: {}", name);

        Product product = productRepository.findByNameAndIsActiveTrue(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name: " + name));

        product.setActive(false);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        logger.info("Product deleted successfully with name: {}", name);
    }

    /**
     * Parses a CSV line handling quoted values and embedded commas properly.
     *
     * <p>This method correctly handles CSV formatting rules including:</p>
     * <ul>
     *   <li>Quoted values containing commas</li>
     *   <li>Escaped quotes within quoted values</li>
     *   <li>Multi-line values (though not common in this context)</li>
     * </ul>
     *
     * @param line the CSV line to parse
     * @return String[] array of parsed values
     */
    private String[] parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentValue.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }

        values.add(currentValue.toString());
        return values.toArray(new String[0]);
    }

    /**
     * Escapes CSV special characters in a value to ensure proper CSV formatting.
     *
     * <p>Values containing commas, quotes, newlines, or carriage returns are wrapped in quotes.
     * Internal quotes are escaped by doubling them.</p>
     *
     * @param value the value to escape, can be null
     * @return String the properly escaped CSV value, empty string if input is null
     */
    private String escapeCsvValue(String value) {
        if (value == null) {
            return "";
        }

        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }
}