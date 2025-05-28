package com.example.orderservice.controller;

import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.inDTO.OrderUpdateInDTO;
import com.example.orderservice.outDTO.OrderOutDTO;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing orders.
 * Provides endpoints to create, retrieve, update, and delete orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param dto the data transfer object containing order details
     * @return the created order wrapped in a ResponseEntity with HTTP status 201 (Created)
     */
    @PostMapping
    ResponseEntity<OrderOutDTO> createOrder(@Valid @RequestBody OrderInDTO dto) {
        logger.info("Creating order for customer: {}", dto.getCustomerEmail());
        OrderOutDTO createdOrder = orderService.createOrder(dto);
        logger.debug("Order created: {}", createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Deletes an existing order by its ID.
     *
     * @param id the ID of the order to be deleted
     * @return a ResponseEntity with HTTP status 204 (No Content) if deletion is successful
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        logger.info("Deleting order with ID: {}", id);
        orderService.deleteOrder(id);
        logger.debug("Order with ID {} deleted successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the requested order wrapped in a ResponseEntity with HTTP status 200 (OK)
     */
    @GetMapping("{id}")
    ResponseEntity<OrderOutDTO> getOrderById(@PathVariable("id") Long id) {
        logger.info("Fetching order with ID: {}", id);
        OrderOutDTO order = orderService.getOrderById(id);
        logger.debug("Fetched order: {}", order);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders wrapped in a ResponseEntity with HTTP status 200 (OK)
     */
    @GetMapping
    ResponseEntity<List<OrderOutDTO>> getAllOrders() {
        logger.info("Fetching all orders");
        List<OrderOutDTO> orders = orderService.getAllOrders();
        logger.debug("Total orders fetched: {}", orders.size());
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    /**
     * Updates the status of an existing order.
     *
     * @param id the ID of the order to be updated
     * @param dto the data transfer object containing updated order status
     * @return the updated order wrapped in a ResponseEntity with HTTP status 201 (Created)
     */
    @PutMapping("{id}")
    ResponseEntity<OrderOutDTO> updateOrderStatus(@PathVariable("id") Long id, @Valid @RequestBody OrderUpdateInDTO dto) {
        logger.info("Updating order status for ID: {} to {}", id, dto.getStatus());
        OrderOutDTO updatedOrder = orderService.updateOrderStatus(id, dto);
        logger.debug("Updated order: {}", updatedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedOrder);
    }
}
