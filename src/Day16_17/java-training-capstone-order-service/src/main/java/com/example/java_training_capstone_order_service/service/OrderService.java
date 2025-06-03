package com.example.java_training_capstone_order_service.service;

import com.example.java_training_capstone_order_service.inDTO.OrderInDTO;
import com.example.java_training_capstone_order_service.inDTO.OrderStatusUpdateInDTO;
import com.example.java_training_capstone_order_service.outDTO.OrderOutDTO;

import java.util.List;

/**
 * Service interface for managing order operations in the e-commerce system.
 *
 * <p>This interface defines the contract for order management services, providing
 * comprehensive CRUD operations and business logic for order processing. Implementations
 * of this interface should handle order lifecycle management, status transitions,
 * and integration with external services.
 *
 * <p>The service manages orders through various states in their lifecycle:
 * <ul>
 * <li><strong>PENDING</strong> - Initial state when order is created</li>
 * <li><strong>CONFIRMED</strong> - Order has been validated and accepted</li>
 * <li><strong>SHIPPED</strong> - Order has been dispatched for delivery</li>
 * <li><strong>DELIVERED</strong> - Order has been successfully delivered</li>
 * <li><strong>CANCELLED</strong> - Order has been cancelled by customer or system</li>
 * </ul>
 *
 * <p>Key responsibilities include:
 * <ul>
 * <li>Order creation with product validation</li>
 * <li>Status management with business rule enforcement</li>
 * <li>Order retrieval and querying</li>
 * <li>Soft deletion for data integrity</li>
 * <li>Integration with notification systems</li>
 * </ul>
 *
 * <p>All methods in this interface may throw runtime exceptions for error conditions
 * such as invalid data, resource not found, or business rule violations. Implementations
 * should provide appropriate error handling and logging.
 *
 * @see OrderInDTO
 * @see OrderOutDTO
 * @see OrderStatusUpdateInDTO
 */
public interface OrderService {

    /**
     * Creates a new order in the system with comprehensive validation.
     *
     * <p>This method handles the complete order creation process including:
     * <ul>
     * <li>Product existence validation via external service calls</li>
     * <li>Order data validation and processing</li>
     * <li>Initial status assignment (typically PENDING)</li>
     * <li>Persistence to the data store</li>
     * <li>Notification dispatch to relevant systems</li>
     * </ul>
     *
     * <p>The method ensures that only orders with valid products can be created
     * and maintains data consistency throughout the creation process. Any failure
     * in the creation pipeline will result in appropriate exception handling.
     *
     * <p><strong>Business Rules:</strong>
     * <ul>
     * <li>Product must exist and be available</li>
     * <li>Order data must pass validation rules</li>
     * <li>Customer information must be valid</li>
     * <li>Quantity must be positive and within limits</li>
     * </ul>
     *
     * @param orderInDTO the input data transfer object containing all necessary
     *                   order information including product ID, customer details,
     *                   quantity, and any special instructions
     * @return OrderOutDTO the created order with generated unique identifier,
     *                     timestamps, calculated totals, and initial status
     * @throws com.example.java_training_capstone_order_service.exception.ResourceInvalidException
     *         if the input data is invalid, product doesn't exist, or creation fails
     * @throws RuntimeException if there's an unexpected system error during creation
     *
     * @see OrderInDTO
     * @see OrderOutDTO
     */
    OrderOutDTO createOrder(OrderInDTO orderInDTO);

    /**
     * Updates the status of an existing order with business rule validation.
     *
     * <p>This method manages order status transitions according to predefined
     * business rules and workflows. It ensures that only valid status changes
     * are allowed and maintains the integrity of the order lifecycle.
     *
     * <p><strong>Valid Status Transitions:</strong>
     * <table border="1" style="border-collapse: collapse;">
     * <tr><th>From Status</th><th>Allowed Transitions</th><th>Description</th></tr>
     * <tr><td>PENDING</td><td>CONFIRMED, CANCELLED</td><td>Order can be confirmed or cancelled</td></tr>
     * <tr><td>CONFIRMED</td><td>SHIPPED, CANCELLED</td><td>Confirmed order can be shipped or cancelled</td></tr>
     * <tr><td>SHIPPED</td><td>DELIVERED</td><td>Shipped order can only be marked as delivered</td></tr>
     * <tr><td>DELIVERED</td><td>None</td><td>Terminal state - no further changes allowed</td></tr>
     * <tr><td>CANCELLED</td><td>None</td><td>Terminal state - no further changes allowed</td></tr>
     * </table>
     *
     * <p>The method also triggers appropriate notifications to inform stakeholders
     * about the status change, enabling downstream processes to react accordingly.
     *
     * @param orderId the unique identifier of the order to update
     * @param statusUpdateDTO the data transfer object containing the new status
     *                        and any additional update information
     * @return OrderOutDTO the updated order with new status, updated timestamp,
     *                     and any other modified fields
     * @throws com.example.java_training_capstone_order_service.exception.ResourceNotFoundException
     *         if no active order exists with the specified ID
     * @throws com.example.java_training_capstone_order_service.exception.ResourceInvalidException
     *         if the status transition is not allowed or update fails
     *
     * @see OrderStatusUpdateInDTO
     * @see OrderOutDTO
     */
    OrderOutDTO updateOrderStatus(String orderId, OrderStatusUpdateInDTO statusUpdateDTO);

    /**
     * Retrieves all active orders in the system.
     *
     * <p>This method returns a comprehensive list of all orders that are currently
     * active (not soft deleted) in the system. The orders are returned as data
     * transfer objects suitable for client consumption.
     *
     * <p>The method provides a complete view of the order landscape and can be
     * used for administrative purposes, reporting, or bulk operations. Each order
     * in the returned list contains full order details including status, timestamps,
     * product information, and customer data.
     *
     * <p><strong>Performance Considerations:</strong>
     * <ul>
     * <li>This method may return large datasets in production systems</li>
     * <li>Consider implementing pagination for better performance</li>
     * <li>Results include only active orders (soft-deleted orders are excluded)</li>
     * </ul>
     *
     * @return List&lt;OrderOutDTO&gt; a list containing all active orders in the system,
     *         each order represented as a complete DTO with all relevant information
     * @throws com.example.java_training_capstone_order_service.exception.ResourceNotFoundException
     *         if no orders exist in the system
     * @throws com.example.java_training_capstone_order_service.exception.ResourceInvalidException
     *         if there's an error retrieving the orders from the data store
     *
     * @see OrderOutDTO
     */
    List<OrderOutDTO> getAllOrders();

    /**
     * Retrieves a specific order by its unique identifier.
     *
     * <p>This method provides detailed information about a single order, including
     * all associated data such as product details, customer information, status
     * history, and calculated totals. The method only returns active orders and
     * will not retrieve soft-deleted orders.
     *
     * <p>The returned order data is complete and suitable for display in user
     * interfaces, API responses, or further processing. All monetary values,
     * dates, and status information are included in the response.
     *
     * <p><strong>Use Cases:</strong>
     * <ul>
     * <li>Order detail pages in customer portals</li>
     * <li>Administrative order management interfaces</li>
     * <li>API endpoints for order information retrieval</li>
     * <li>Integration with external systems requiring order data</li>
     * </ul>
     *
     * @param orderId the unique identifier of the order to retrieve, must be
     *                a valid non-null string representing an existing order
     * @return OrderOutDTO the complete order information including all details,
     *                     status, timestamps, and associated data
     * @throws com.example.java_training_capstone_order_service.exception.ResourceNotFoundException
     *         if no active order exists with the specified identifier
     * @throws com.example.java_training_capstone_order_service.exception.ResourceInvalidException
     *         if there's an error during the retrieval process
     *
     * @see OrderOutDTO
     */
    OrderOutDTO getOrderById(String orderId);

    /**
     * Performs a soft deletion of an order based on business rules.
     *
     * <p>This method implements soft deletion rather than physical removal to
     * maintain data integrity, audit trails, and referential consistency. The
     * order is marked as inactive but remains in the database for historical
     * and compliance purposes.
     *
     * <p><strong>Deletion Rules:</strong>
     * <ul>
     * <li><strong>Allowed:</strong> PENDING, CONFIRMED, or CANCELLED orders</li>
     * <li><strong>Prohibited:</strong> SHIPPED or DELIVERED orders</li>
     * </ul>
     *
     * <p>Orders that have been shipped or delivered cannot be deleted due to
     * business and legal requirements. These orders represent completed or
     * in-progress transactions that must be preserved for record-keeping.
     *
     * <p><strong>Effects of Deletion:</strong>
     * <ul>
     * <li>Order is marked as inactive in the database</li>
     * <li>Order will not appear in active order queries</li>
     * <li>Historical data and audit trails are preserved</li>
     * <li>Related data integrity is maintained</li>
     * </ul>
     *
     * <p>The method is idempotent - attempting to delete an already deleted
     * order will result in a ResourceNotFoundException rather than an error.
     *
     * @param orderId the unique identifier of the order to delete, must be
     *                a valid non-null string representing an existing active order
     * @throws com.example.java_training_capstone_order_service.exception.ResourceNotFoundException
     *         if no active order exists with the specified identifier
     * @throws com.example.java_training_capstone_order_service.exception.ResourceInvalidException
     *         if the order status prevents deletion (SHIPPED or DELIVERED) or
     *         if the deletion operation fails
     *
     * @see #getOrderById(String)
     */
    void deleteOrder(String orderId);
}