package com.example.java_training_capstone_order_service.mapper;

import com.example.java_training_capstone_order_service.entity.Order;
import com.example.java_training_capstone_order_service.inDTO.OrderInDTO;
import com.example.java_training_capstone_order_service.outDTO.OrderOutDTO;
import com.example.java_training_capstone_order_service.outDTO.ProductOutDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class responsible for converting between Order entities and DTOs.
 * Provides mapping methods to convert from inbound DTOs to entities, from entities to outbound DTOs,
 * and for updating order status or performing soft deletion.
 */
public class OrderMapper {

    /**
     * Converts an {@link OrderInDTO} and a {@link ProductOutDTO} to an {@link Order} entity.
     * Populates fields like totalAmount based on product price and quantity.
     *
     * @param orderInDTO    the incoming order data from the client
     * @param productOutDTO the product details fetched from another service
     * @return the constructed Order entity
     */
    public static Order convertInDTOToEntity(OrderInDTO orderInDTO, ProductOutDTO productOutDTO) {
        if (orderInDTO == null) {
            return null;
        }

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setProductId(orderInDTO.getProductId());
        order.setProductName(productOutDTO.getName());
        order.setQuantity(orderInDTO.getQuantity());
        order.setUnitPrice(productOutDTO.getPrice());
        order.setTotalAmount(productOutDTO.getPrice().multiply(BigDecimal.valueOf(orderInDTO.getQuantity())));
        order.setCustomerEmail(orderInDTO.getCustomerEmail());
        order.setStatus(orderInDTO.getStatus() != null ? orderInDTO.getStatus() : "PENDING");
        order.setShippingAddress(orderInDTO.getShippingAddress());
        order.setIsActive(true);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return order;
    }

    /**
     * Converts an {@link Order} entity to an {@link OrderOutDTO}.
     *
     * @param order the order entity to convert
     * @return the DTO representation of the order
     */
    public static OrderOutDTO convertEntityToOutDTO(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderOutDTO(
                order.getId(),
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getTotalAmount(),
                order.getCustomerEmail(),
                order.getStatus(),
                order.getShippingAddress(),
                order.getIsActive(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    /**
     * Converts a list of {@link Order} entities to a list of {@link OrderOutDTO}s.
     *
     * @param orders the list of order entities
     * @return a list of DTOs representing the orders
     */
    public static List<OrderOutDTO> convertEntityListToOutDTOList(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(OrderMapper::convertEntityToOutDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates the status and update timestamp of an existing {@link Order} entity.
     *
     * @param existingOrder the existing order to update
     * @param newStatus     the new status value
     * @return the updated Order entity
     */
    public static Order updateOrderStatus(Order existingOrder, String newStatus) {
        if (existingOrder == null || newStatus == null) {
            return existingOrder;
        }

        existingOrder.setStatus(newStatus);
        existingOrder.setUpdatedAt(LocalDateTime.now());

        return existingOrder;
    }

    /**
     * Performs a soft delete on the given {@link Order} entity by setting `isActive` to false
     * and updating the timestamp.
     *
     * @param existingOrder the order to soft delete
     * @return the soft-deleted Order entity
     */
    public static Order softDeleteOrder(Order existingOrder) {
        if (existingOrder == null) {
            return existingOrder;
        }

        existingOrder.setIsActive(false);
        existingOrder.setUpdatedAt(LocalDateTime.now());

        return existingOrder;
    }
}
