package com.example.orderservice.serviceImpl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.exception.ResourceInvalidException;
import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.inDTO.OrderUpdateInDTO;
import com.example.orderservice.kafka.OrderKafkaProducer;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.outDTO.OrderOutDTO;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing orders.
 * Handles business logic related to order creation, retrieval, update, and deletion.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderKafkaProducer orderKafkaProducer;

    /**
     * Creates a new order from the given input DTO.
     * Sends a Kafka event after successful creation.
     *
     * @param dto the order input data transfer object
     * @return the created order as an output DTO
     */
    @Override
    public OrderOutDTO createOrder(OrderInDTO dto) {
        logger.info("Creating order for customer: {}", dto.getCustomerEmail());

        Order order = OrderMapper.toEntity(dto);
        Order savedOrder = orderRepository.save(order);
        OrderOutDTO orderOutDTO = OrderMapper.toDTO(savedOrder);

        orderKafkaProducer.sendOrderCreatedEvent(orderOutDTO);
        logger.debug("Order created and event sent: {}", orderOutDTO);

        return orderOutDTO;
    }

    /**
     * Deletes an order by its ID after validation.
     *
     * @param id the ID of the order to delete
     * @throws ResourceInvalidException if the ID is null or invalid
     * @throws ResourceNotFoundException if no order exists with the given ID
     */
    @Override
    public void deleteOrder(Long id) {
        logger.info("Deleting order with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid order ID for deletion: {}", id);
            throw new ResourceInvalidException("Order ID must be a positive number");
        }

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            logger.warn("Order not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }

        orderRepository.deleteById(id);
        logger.debug("Order deleted successfully with ID: {}", id);
    }

    /**
     * Retrieves an order by its ID after validation.
     *
     * @param id the ID of the order to retrieve
     * @return the order output DTO
     * @throws ResourceInvalidException if the ID is null or invalid
     * @throws ResourceNotFoundException if no order exists with the given ID
     */
    @Override
    public OrderOutDTO getOrderById(Long id) {
        logger.info("Fetching order with ID: {}", id);

        if (id == null || id <= 0) {
            logger.warn("Invalid order ID for fetching: {}", id);
            throw new ResourceInvalidException("Order ID must be a positive number");
        }

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            logger.warn("Order not found with ID: {}", id);
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }

        OrderOutDTO dto = OrderMapper.toDTO(orderOptional.get());
        logger.debug("Fetched order: {}", dto);
        return dto;
    }

    /**
     * Retrieves all orders.
     *
     * @return a list of order output DTOs
     */
    @Override
    public List<OrderOutDTO> getAllOrders() {
        logger.info("Fetching all orders");

        List<Order> orders = orderRepository.findAll();
        List<OrderOutDTO> dtos = orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());

        logger.debug("Total orders fetched: {}", dtos.size());
        return dtos;
    }

    /**
     * Updates the status of an existing order with validation on status transitions.
     *
     * @param id the ID of the order to update
     * @param dto the DTO containing the new status
     * @return the updated order output DTO
     * @throws ResourceInvalidException if the ID is invalid or status transition is not allowed
     * @throws ResourceNotFoundException if no order exists with the given ID
     */
    @Override
    public OrderOutDTO updateOrderStatus(Long id, OrderUpdateInDTO dto) {
        logger.info("Updating order status for ID: {} to {}", id, dto.getStatus());

        if (id == null || id <= 0) {
            logger.warn("Invalid order ID for status update: {}", id);
            throw new ResourceInvalidException("Order ID must be a positive number");
        }

        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            logger.warn("Order not found with ID: {}", id);
            throw new ResourceNotFoundException("Order not found with ID: " + id);
        }

        Order existingOrder = orderOptional.get();
        String currentStatus = existingOrder.getStatus();
        String newStatus = dto.getStatus().trim();

        if (!isValidStatusTransition(currentStatus, newStatus)) {
            logger.warn("Invalid status transition from '{}' to '{}' for order ID {}", currentStatus, newStatus, id);
            throw new ResourceInvalidException(
                    "Invalid status transition from '" + currentStatus + "' to '" + newStatus + "'");
        }

        existingOrder.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(existingOrder);

        OrderOutDTO updatedDto = OrderMapper.toDTO(updatedOrder);
        logger.debug("Order status updated successfully: {}", updatedDto);

        return updatedDto;
    }

    /**
     * Validates if the status transition is allowed according to business rules:
     * <ul>
     *   <li>Ordered -> Processing (allowed)</li>
     *   <li>Ordered -> Completed (allowed, direct completion)</li>
     *   <li>Processing -> Completed (allowed)</li>
     *   <li>No transitions from Completed (not allowed)</li>
     *   <li>No backward transitions (not allowed)</li>
     * </ul>
     *
     * @param currentStatus the current order status
     * @param newStatus the new order status requested
     * @return true if transition is valid, false otherwise
     */
    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        if (currentStatus.equals(newStatus)) {
            return false;
        }

        switch (currentStatus) {
            case "Ordered":
                return "Processing".equals(newStatus) || "Completed".equals(newStatus);
            case "Processing":
                return "Completed".equals(newStatus);
            default:
                return false;
        }
    }
}
