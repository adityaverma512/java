package com.example.java_training_capstone_order_service.controller;

import com.example.java_training_capstone_order_service.inDTO.OrderInDTO;
import com.example.java_training_capstone_order_service.inDTO.OrderStatusUpdateInDTO;
import com.example.java_training_capstone_order_service.outDTO.OrderOutDTO;
import com.example.java_training_capstone_order_service.outDTO.StandardResponseOutDTO;
import com.example.java_training_capstone_order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing order operations in the Order Service.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting orders.
 * All endpoints return standardized responses wrapped in {@link StandardResponseOutDTO}.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    /**
     * Service layer dependency for handling order business logic.
     */
    @Autowired
    private OrderService orderService;

    /**
     * Creates a new order in the system.
     * <p>
     * This endpoint accepts order details and creates a new order record.
     * The request body is validated using Jakarta Bean Validation annotations.
     * </p>
     *
     * @param orderInDTO the order data transfer object containing order details
     * @return ResponseEntity containing the created order wrapped in a standard response
     * @apiNote POST /api/v1/orders
     * @apiNote Request Body: OrderInDTO (JSON)
     * @apiNote Response: StandardResponseOutDTO&lt;OrderOutDTO&gt; with HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<StandardResponseOutDTO<OrderOutDTO>> createOrder(
            @Valid @RequestBody OrderInDTO orderInDTO) {

        OrderOutDTO createdOrder = orderService.createOrder(orderInDTO);

        StandardResponseOutDTO<OrderOutDTO> response = StandardResponseOutDTO.success(
                createdOrder,
                "Order created successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific order by its unique identifier.
     * <p>
     * This endpoint fetches order details for the given order ID.
     * If the order is not found, appropriate error response is returned.
     * </p>
     *
     * @param orderId the unique identifier of the order to retrieve
     * @return ResponseEntity containing the order details wrapped in a standard response
     * @throws com.example.java_training_capstone_order_service.exception.ResourceNotFoundException if no order exists with the given ID
     *
     * @apiNote GET /api/v1/orders/{orderId}
     * @apiNote Path Parameter: orderId (String)
     * @apiNote Response: StandardResponseOutDTO&lt;OrderOutDTO&gt; with HTTP 200 OK
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<StandardResponseOutDTO<OrderOutDTO>> getOrderById(
            @PathVariable String orderId) {

        OrderOutDTO order = orderService.getOrderById(orderId);

        StandardResponseOutDTO<OrderOutDTO> response = StandardResponseOutDTO.success(
                order,
                "Order retrieved successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all orders from the system.
     * <p>
     * This endpoint fetches all available orders. The list may be empty
     * if no orders exist in the system.
     * </p>
     *
     * @return ResponseEntity containing a list of all orders wrapped in a standard response
     *
     * @apiNote GET /api/v1/orders
     * @apiNote Response: StandardResponseOutDTO&lt;List&lt;OrderOutDTO&gt;&gt; with HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<StandardResponseOutDTO<List<OrderOutDTO>>> getAllOrders() {

        List<OrderOutDTO> orders = orderService.getAllOrders();

        StandardResponseOutDTO<List<OrderOutDTO>> response = StandardResponseOutDTO.success(
                orders,
                "Orders retrieved successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates the status of an existing order.
     * <p>
     * This endpoint allows modification of an order's status using the provided
     * status update information. The request body is validated before processing.
     * </p>
     *
     * @param orderId the unique identifier of the order to update
     * @param statusUpdateDTO the data transfer object containing new status information
     * @return ResponseEntity containing the updated order wrapped in a standard response
     * @apiNote PUT /api/v1/orders/{orderId}
     * @apiNote Path Parameter: orderId (String)
     * @apiNote Request Body: OrderStatusUpdateInDTO (JSON)
     * @apiNote Response: StandardResponseOutDTO&lt;OrderOutDTO&gt; with HTTP 200 OK
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<StandardResponseOutDTO<OrderOutDTO>> updateOrderStatus(
            @PathVariable String orderId,
            @Valid @RequestBody OrderStatusUpdateInDTO statusUpdateDTO) {

        OrderOutDTO updatedOrder = orderService.updateOrderStatus(orderId, statusUpdateDTO);

        StandardResponseOutDTO<OrderOutDTO> response = StandardResponseOutDTO.success(
                updatedOrder,
                "Order status updated successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Performs a soft delete operation on an order.
     * <p>
     * This endpoint marks an order as deleted without permanently removing it from the database.
     * The order will no longer be visible in regular queries but can be recovered if needed.
     * </p>
     *
     * @param orderId the unique identifier of the order to delete
     * @return ResponseEntity containing a success message wrapped in a standard response
     * @throws com.example.java_training_capstone_order_service.exception.ResourceNotFoundException if no order exists with the given ID
     * @apiNote DELETE /api/v1/orders/{orderId}
     * @apiNote Path Parameter: orderId (String)
     * @apiNote Response: StandardResponseOutDTO&lt;String&gt; with HTTP 200 OK
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<StandardResponseOutDTO<String>> deleteOrder(
            @PathVariable String orderId) {

        orderService.deleteOrder(orderId);

        StandardResponseOutDTO<String> response = StandardResponseOutDTO.success(
                "Order with ID: " + orderId + " has been deleted successfully",
                "Order deleted successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}