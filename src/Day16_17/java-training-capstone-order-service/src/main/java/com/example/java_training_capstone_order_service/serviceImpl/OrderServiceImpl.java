package com.example.java_training_capstone_order_service.serviceImpl;

import com.example.java_training_capstone_order_service.entity.Order;
import com.example.java_training_capstone_order_service.exception.ResourceInvalidException;
import com.example.java_training_capstone_order_service.exception.ResourceNotFoundException;
import com.example.java_training_capstone_order_service.feignClient.ProductServiceFeignClient;
import com.example.java_training_capstone_order_service.inDTO.OrderInDTO;
import com.example.java_training_capstone_order_service.inDTO.OrderStatusUpdateInDTO;
import com.example.java_training_capstone_order_service.kafka.OrderNotificationService;
import com.example.java_training_capstone_order_service.mapper.OrderMapper;
import com.example.java_training_capstone_order_service.outDTO.OrderNotificationOutDTO;
import com.example.java_training_capstone_order_service.outDTO.OrderOutDTO;
import com.example.java_training_capstone_order_service.outDTO.ProductOutDTO;
import com.example.java_training_capstone_order_service.outDTO.StandardResponseOutDTO;
import com.example.java_training_capstone_order_service.repository.OrderRepository;
import com.example.java_training_capstone_order_service.service.OrderService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the OrderService interface that provides comprehensive order management functionality.
 * This service handles order creation, status updates, deletion, and retrieval operations while integrating
 * with external product services and notification systems.
 *
 * <p>Key features include:
 * <ul>
 * <li>Order lifecycle management with status validation</li>
 * <li>Integration with product service via Feign client</li>
 * <li>Kafka-based notification system for order events</li>
 * <li>Soft delete functionality for order management</li>
 * <li>Comprehensive error handling and logging</li>
 * </ul>
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;

    @Autowired
    private OrderNotificationService orderNotificationService;

    /**
     * List of valid order statuses that can be used in the system.
     * Status transitions are validated against predefined rules.
     */
    private static final List<String> VALID_STATUSES = Arrays.asList(
            "PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"
    );

    /**
     * Creates a new order in the system after validating the associated product.
     *
     * <p>This method performs the following operations:
     * <ol>
     * <li>Validates the product exists via external product service</li>
     * <li>Creates and saves the order entity</li>
     * <li>Sends order creation notification via Kafka</li>
     * <li>Returns the created order as DTO</li>
     * </ol>
     *
     * <p>If the product validation fails or any error occurs during order creation,
     * appropriate exceptions are thrown with detailed error messages.
     *
     * @param orderInDTO the input DTO containing order details including product ID, customer info, etc.
     * @return OrderOutDTO the created order with generated ID and timestamps
     * @throws ResourceInvalidException if the product is not found, response is invalid, or order creation fails
     * @throws RuntimeException if there's an unexpected error during the process
     *
     * @see OrderInDTO
     * @see OrderOutDTO
     * @see ProductServiceFeignClient#getProductById(String)
     */
    @Override
    public OrderOutDTO createOrder(OrderInDTO orderInDTO) {
        try {
            ResponseEntity<StandardResponseOutDTO<ProductOutDTO>> response = productServiceFeignClient.getProductById(orderInDTO.getProductId());
            StandardResponseOutDTO<ProductOutDTO> apiResponse = response.getBody();

            if (apiResponse == null || apiResponse.getData() == null) {
                throw new ResourceInvalidException("Product not found or response is invalid for productId: " + orderInDTO.getProductId());
            }
            ProductOutDTO productOutDTO = apiResponse.getData();
            Order order = OrderMapper.convertInDTOToEntity(orderInDTO, productOutDTO);
            Order savedOrder = orderRepository.save(order);
            OrderOutDTO orderOutDTO = OrderMapper.convertEntityToOutDTO(savedOrder);
            try {
                OrderNotificationOutDTO notification = OrderNotificationOutDTO.fromOrderOutDTO(orderOutDTO, "ORDER_CREATED");
                orderNotificationService.sendOrderNotification(notification);
                logger.info("Order creation notification sent for order ID: {}", orderOutDTO.getId());
            } catch (Exception kafkaException) {
                logger.error("Failed to send order creation notification for order ID: {}. Error: {}",
                        orderOutDTO.getId(), kafkaException.getMessage(), kafkaException);
            }
            return orderOutDTO;
        } catch (FeignException e) {
            logger.error("Feign Exception {}", e.getMessage());
            throw new RuntimeException("Unable to connect to order service");
        } catch (Exception e) {
            logger.error("Failed to create order: {}", e.getMessage());
            throw new ResourceInvalidException("Failed to create order.");
        }
    }

    /**
     * Updates the status of an existing order with comprehensive validation.
     *
     * <p>This method enforces business rules for status transitions:
     * <ul>
     * <li>PENDING → CONFIRMED or CANCELLED</li>
     * <li>CONFIRMED → SHIPPED or CANCELLED</li>
     * <li>SHIPPED → DELIVERED only</li>
     * <li>DELIVERED and CANCELLED are terminal states</li>
     * </ul>
     *
     * <p>After successful status update, a notification is sent via Kafka to inform
     * other services and stakeholders about the status change.
     *
     * @param orderId the unique identifier of the order to update
     * @param statusUpdateDTO DTO containing the new status to be applied
     * @return OrderOutDTO the updated order with new status
     * @throws ResourceNotFoundException if no active order is found with the given ID
     * @throws ResourceInvalidException if the status transition is invalid or update fails
     *
     * @see OrderStatusUpdateInDTO
     * @see #validateStatusTransition(String, String)
     */
    @Override
    public OrderOutDTO updateOrderStatus(String orderId, OrderStatusUpdateInDTO statusUpdateDTO) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndIsActive(orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }

        Order existingOrder = optionalOrder.get();
        String currentStatus = existingOrder.getStatus();
        String newStatus = statusUpdateDTO.getStatus();

        validateStatusTransition(currentStatus, newStatus);

        Order updatedOrder = OrderMapper.updateOrderStatus(existingOrder, newStatus);

        try {
            Order savedOrder = orderRepository.save(updatedOrder);

            OrderOutDTO orderOutDTO = OrderMapper.convertEntityToOutDTO(savedOrder);

            try {
                OrderNotificationOutDTO notification = OrderNotificationOutDTO.fromOrderOutDTO(orderOutDTO, "ORDER_STATUS_UPDATED");
                orderNotificationService.sendOrderNotification(notification);
                logger.info("Order status update notification sent for order ID: {} with new status: {}",
                        orderOutDTO.getId(), newStatus);
            } catch (Exception kafkaException) {
                logger.error("Failed to send order status update notification for order ID: {}. Error: {}",
                        orderOutDTO.getId(), kafkaException.getMessage(), kafkaException);
            }

            return orderOutDTO;
        } catch (Exception e) {
            logger.error("Failed to update order status: " + e.getMessage());
            throw new ResourceInvalidException("Failed to update order status.");
        }
    }

    /**
     * Performs a soft delete of an order by marking it as inactive.
     *
     * <p>This method implements business rules for order deletion:
     * <ul>
     * <li>Only PENDING, CONFIRMED, or CANCELLED orders can be deleted</li>
     * <li>SHIPPED and DELIVERED orders cannot be deleted due to business constraints</li>
     * <li>Deletion is performed as a soft delete (marking as inactive) rather than physical removal</li>
     * </ul>
     *
     * <p>The soft delete approach maintains data integrity and audit trails while
     * removing the order from active business operations.
     *
     * @param orderId the unique identifier of the order to delete
     * @throws ResourceNotFoundException if no active order is found with the given ID
     * @throws ResourceInvalidException if the order status prevents deletion or if the delete operation fails
     *
     * @see OrderMapper#softDeleteOrder(Order)
     */
    @Override
    public void deleteOrder(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndIsActive(orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException("Active order not found with ID: " + orderId);
        }

        Order existingOrder = optionalOrder.get();

        if ("SHIPPED".equals(existingOrder.getStatus()) || "DELIVERED".equals(existingOrder.getStatus())) {
            throw new ResourceInvalidException(
                    "Cannot delete order with status: " + existingOrder.getStatus() +
                            ". Only PENDING, CONFIRMED, or CANCELLED orders can be deleted."
            );
        }

        Order deletedOrder = OrderMapper.softDeleteOrder(existingOrder);

        try {
            orderRepository.save(deletedOrder);
        } catch (Exception e) {
            throw new ResourceInvalidException("Failed to delete order: " + e.getMessage());
        }
    }

    /**
     * Retrieves a single active order by its unique identifier.
     *
     * <p>This method only returns orders that are marked as active (not soft deleted).
     * The returned order includes all relevant information converted to the output DTO format.
     *
     * @param orderId the unique identifier of the order to retrieve
     * @return OrderOutDTO the order details if found and active
     * @throws ResourceNotFoundException if no active order exists with the given ID
     * @throws ResourceInvalidException if there's an error during the retrieval process
     *
     * @see OrderOutDTO
     * @see OrderMapper#convertEntityToOutDTO(Order)
     */
    @Override
    public OrderOutDTO getOrderById(String orderId) {
        try {
            Optional<Order> optionalOrder = orderRepository.findByIdAndIsActive(orderId);

            if (optionalOrder.isEmpty()) {
                throw new ResourceNotFoundException("Order not found with ID: " + orderId);
            }

            Order order = optionalOrder.get();

            return OrderMapper.convertEntityToOutDTO(order);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceInvalidException("Failed to retrieve order: " + e.getMessage());
        }
    }

    /**
     * Retrieves all active orders in the system.
     *
     * <p>This method returns a list of all orders that are currently active (not soft deleted).
     * Each order is converted to the appropriate output DTO format for client consumption.
     *
     * <p>If no active orders are found in the system, a ResourceNotFoundException is thrown
     * to indicate that the collection is empty.
     *
     * @return List&lt;OrderOutDTO&gt; a list of all active orders, empty list is never returned
     * @throws ResourceNotFoundException if no active orders exist in the system
     * @throws ResourceInvalidException if there's an error during the retrieval process
     *
     * @see OrderOutDTO
     * @see OrderMapper#convertEntityToOutDTO(Order)
     */
    @Override
    public List<OrderOutDTO> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAllByIsActive();
            if(orders.isEmpty()) {
                throw new ResourceNotFoundException("No Orders Found");
            }

            return orders.stream()
                    .map(OrderMapper::convertEntityToOutDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new ResourceInvalidException("Failed to retrieve orders: " + e.getMessage());
        }
    }

    /**
     * Validates whether a status transition from current status to new status is allowed.
     *
     * <p>This method enforces the following business rules for order status transitions:
     *
     * <table border="1">
     * <tr><th>Current Status</th><th>Allowed Transitions</th></tr>
     * <tr><td>PENDING</td><td>CONFIRMED, CANCELLED</td></tr>
     * <tr><td>CONFIRMED</td><td>SHIPPED, CANCELLED</td></tr>
     * <tr><td>SHIPPED</td><td>DELIVERED</td></tr>
     * <tr><td>DELIVERED</td><td>None (Terminal state)</td></tr>
     * <tr><td>CANCELLED</td><td>None (Terminal state)</td></tr>
     * </table>
     *
     * <p>The validation also prevents setting the same status (no-op transitions)
     * and handles unknown status values appropriately.
     *
     * @param currentStatus the current status of the order
     * @param newStatus the desired new status for the order
     * @throws ResourceInvalidException if the transition is not allowed, statuses are the same,
     *                                 or if an unknown status is encountered
     *
     * @see #VALID_STATUSES
     */
    private void validateStatusTransition(String currentStatus, String newStatus) {
        if (currentStatus.equals(newStatus)) {
            throw new ResourceInvalidException("Order is already in " + newStatus + " status");
        }

        switch (currentStatus) {
            case "PENDING":
                if (!Arrays.asList("CONFIRMED", "CANCELLED").contains(newStatus)) {
                    throw new ResourceInvalidException(
                            "Invalid status transition from PENDING to " + newStatus +
                                    ". Valid transitions: CONFIRMED, CANCELLED"
                    );
                }
                break;
            case "CONFIRMED":
                if (!Arrays.asList("SHIPPED", "CANCELLED").contains(newStatus)) {
                    throw new ResourceInvalidException(
                            "Invalid status transition from CONFIRMED to " + newStatus +
                                    ". Valid transitions: SHIPPED, CANCELLED"
                    );
                }
                break;
            case "SHIPPED":
                if (!"DELIVERED".equals(newStatus)) {
                    throw new ResourceInvalidException(
                            "Invalid status transition from SHIPPED to " + newStatus +
                                    ". Valid transition: DELIVERED"
                    );
                }
                break;
            case "DELIVERED":
                throw new ResourceInvalidException(
                        "Cannot change status from DELIVERED. Order is already completed."
                );
            case "CANCELLED":
                throw new ResourceInvalidException(
                        "Cannot change status from CANCELLED. Order is already cancelled."
                );
            default:
                throw new ResourceInvalidException("Unknown current status: " + currentStatus);
        }
    }
}