package com.example.orderservice.mapper;

import com.example.orderservice.entity.Order;
import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.outDTO.OrderOutDTO;

import java.time.LocalDateTime;

/**
 * Utility class for mapping between Order entity and its DTOs.
 * Provides methods to convert from OrderInDTO to Order entity
 * and from Order entity to OrderOutDTO.
 */
public class OrderMapper {

    /**
     * Converts an OrderInDTO object to an Order entity.
     * Sets the order date to the current time and initializes the status to "Ordered".
     *
     * @param dto the OrderInDTO containing order input data
     * @return an Order entity with fields populated from the DTO
     */
    public static Order toEntity(OrderInDTO dto) {
        Order order = new Order();
        order.setCustomerEmail(dto.getCustomerEmail());
        order.setProduct(dto.getProduct());
        order.setQuantity(dto.getQuantity());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Ordered");
        return order;
    }

    /**
     * Converts an Order entity to an OrderOutDTO.
     *
     * @param order the Order entity to convert
     * @return an OrderOutDTO populated with data from the entity
     */
    public static OrderOutDTO toDTO(Order order) {
        OrderOutDTO dto = new OrderOutDTO();
        dto.setId(order.getId());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setProduct(order.getProduct());
        dto.setQuantity(order.getQuantity());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
