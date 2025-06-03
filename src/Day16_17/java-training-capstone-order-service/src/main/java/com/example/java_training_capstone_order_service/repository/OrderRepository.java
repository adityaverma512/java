package com.example.java_training_capstone_order_service.repository;

import com.example.java_training_capstone_order_service.entity.Order;
import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.google.cloud.spring.data.spanner.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Order entities in Google Cloud Spanner database.
 * This interface extends SpannerRepository to provide CRUD operations and custom
 * query methods for order data access.
 *
 * <p>The repository includes custom query methods that focus on active orders,
 * filtering out inactive or soft-deleted records from the results. This ensures
 * that business logic consistently works with valid, active order data.</p>
 *
 * <p>All custom queries are implemented using Spanner SQL syntax and are optimized
 * for the Cloud Spanner database engine. The repository leverages Spring Data's
 * automatic implementation generation for standard CRUD operations.</p>
 * @see Order
 * @see SpannerRepository
 */
@Repository
public interface OrderRepository extends SpannerRepository<Order, String> {

    /**
     * Finds an active order by its unique identifier.
     * This method retrieves an order only if it exists and is marked as active,
     * effectively filtering out soft-deleted or inactive orders.
     *
     * <p>The query uses a compound condition to ensure both existence and active status,
     * making it safe for business operations that should only work with valid orders.</p>
     *
     * @param orderId the unique identifier of the order to find
     * @return an Optional containing the order if found and active, empty Optional otherwise
     * @throws IllegalArgumentException if orderId is null
     */
    @Query("SELECT * FROM orders WHERE id = @orderId AND is_active = true")
    Optional<Order> findByIdAndIsActive(@Param("orderId") String orderId);

    /**
     * Retrieves all active orders from the database.
     * This method returns only orders that are marked as active, excluding
     * any soft-deleted or inactive order records.
     *
     * <p>The method is useful for listing operations, reports, and bulk processing
     * where only valid, active orders should be considered. The result list
     * may be empty if no active orders exist.</p>
     *
     * @return a list of all active orders; empty list if no active orders are found
     */
    @Query("SELECT * FROM orders WHERE is_active = true")
    List<Order> findAllByIsActive();
}