package com.example.java_training_capstone_order_service.feignClient;

import com.example.java_training_capstone_order_service.outDTO.ProductOutDTO;
import com.example.java_training_capstone_order_service.outDTO.StandardResponseOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for communicating with the Product Service.
 * <p>
 * This interface provides a declarative way to call the Product Service APIs
 * using Spring Cloud OpenFeign. It abstracts the HTTP communication details
 * and provides type-safe method calls for interacting with product-related endpoints.
 * </p>
 *
 * <p>
 * The client is configured to communicate with the Product Service running on
 * localhost:8080 and handles the standard response format used across the application.
 * </p>
 *
 * <p>
 * <strong>Service Configuration:</strong>
 * <ul>
 *   <li>Service Name: java-training-capstone</li>
 *   <li>Base URL: http://localhost:8080/api/v1/products</li>
 *   <li>Response Format: StandardResponseOutDTO wrapper</li>
 * </ul>
 * </p>
 *
 * @author Your Name
 * @version 1.0
 * @since 1.0
 * @see FeignClient
 * @see ProductOutDTO
 * @see StandardResponseOutDTO
 */
@FeignClient(name = "java-training-capstone", url = "http://localhost:8080/api/v1/products")
public interface ProductServiceFeignClient {

    /**
     * Retrieves product information by its unique identifier from the Product Service.
     * <p>
     * This method makes a synchronous HTTP GET request to the Product Service
     * to fetch detailed information about a specific product. The response
     * includes the product data wrapped in a standardized response format.
     * </p>
     *
     * <p>
     * <strong>HTTP Details:</strong>
     * <ul>
     *   <li>Method: GET</li>
     *   <li>Endpoint: /id/{id}</li>
     *   <li>Full URL: http://localhost:8080/api/v1/products/id/{id}</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Error Handling:</strong>
     * The method may throw FeignException for various HTTP error responses:
     * <ul>
     *   <li>404 Not Found - if product with given ID doesn't exist</li>
     *   <li>500 Internal Server Error - if Product Service encounters an error</li>
     *   <li>Connection errors - if Product Service is unavailable</li>
     * </ul>
     * </p>
     *
     * @param id the unique identifier of the product to retrieve (must not be null or empty)
     * @return ResponseEntity containing the product data wrapped in StandardResponseOutDTO
     * @throws feign.FeignException if the HTTP request fails or returns an error status
     * @throws IllegalArgumentException if the id parameter is null or empty
     *
     * @apiNote GET /id/{id}
     * @apiNote Path Parameter: id (String) - Product unique identifier
     * @apiNote Response: StandardResponseOutDTO&lt;ProductOutDTO&gt; with appropriate HTTP status
     *
     * @see ProductOutDTO
     * @see StandardResponseOutDTO
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<StandardResponseOutDTO<ProductOutDTO>> getProductById(@PathVariable String id);
}