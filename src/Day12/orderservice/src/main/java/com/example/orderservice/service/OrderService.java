package com.example.orderservice.service;

import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.inDTO.OrderUpdateInDTO;
import com.example.orderservice.outDTO.OrderOutDTO;

import java.util.List;

public interface OrderService {
    OrderOutDTO createOrder(OrderInDTO dto);
    void deleteOrder(Long id);
    OrderOutDTO getOrderById(Long id);
    List<OrderOutDTO> getAllOrders();
    OrderOutDTO updateOrderStatus(Long id, OrderUpdateInDTO dto);
}
