package com.example.orderservice.ControllerTest;

import com.example.orderservice.controller.OrderController;
import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.inDTO.OrderUpdateInDTO;
import com.example.orderservice.outDTO.OrderOutDTO;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for OrderController using MockMvc.
 */
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderOutDTO sampleOrder;

    @BeforeEach
    void setup() {
        sampleOrder = new OrderOutDTO();
        sampleOrder.setId(1L);
        sampleOrder.setCustomerEmail("test@example.com");
        sampleOrder.setProduct("Test Product");
        sampleOrder.setQuantity(2);
        sampleOrder.setOrderDate(LocalDateTime.of(2025, 5, 28, 10, 0));
        sampleOrder.setStatus("PENDING");
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderInDTO inputDto = new OrderInDTO();
        inputDto.setCustomerEmail("test@gmail.com");
        inputDto.setProduct("Test Product");
        inputDto.setQuantity(2);

        when(orderService.createOrder(ArgumentMatchers.any(OrderInDTO.class))).thenReturn(sampleOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleOrder.getId()))
                .andExpect(jsonPath("$.customerEmail").value(sampleOrder.getCustomerEmail()))
                .andExpect(jsonPath("$.product").value(sampleOrder.getProduct()))
                .andExpect(jsonPath("$.quantity").value(sampleOrder.getQuantity()))
                .andExpect(jsonPath("$.status").value(sampleOrder.getStatus()));

        verify(orderService, times(1)).createOrder(any(OrderInDTO.class));
    }

    @Test
    void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/orders/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void testGetOrderById() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(sampleOrder);

        mockMvc.perform(get("/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleOrder.getId()))
                .andExpect(jsonPath("$.customerEmail").value(sampleOrder.getCustomerEmail()))
                .andExpect(jsonPath("$.product").value(sampleOrder.getProduct()))
                .andExpect(jsonPath("$.quantity").value(sampleOrder.getQuantity()))
                .andExpect(jsonPath("$.status").value(sampleOrder.getStatus()));

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(sampleOrder));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(sampleOrder.getId()))
                .andExpect(jsonPath("$[0].customerEmail").value(sampleOrder.getCustomerEmail()));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testUpdateOrderStatus() throws Exception {
        OrderUpdateInDTO updateDto = new OrderUpdateInDTO();
        updateDto.setStatus("Ordered");

        sampleOrder.setStatus("Ordered");

        when(orderService.updateOrderStatus(eq(1L), any(OrderUpdateInDTO.class))).thenReturn(sampleOrder);

        mockMvc.perform(put("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Ordered"));

        verify(orderService, times(1)).updateOrderStatus(eq(1L), any(OrderUpdateInDTO.class));
    }
}
