package com.example.orderservice.ServiceImplTest;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ResourceInvalidException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.inDTO.OrderUpdateInDTO;
import com.example.orderservice.kafka.OrderKafkaProducer;
import com.example.orderservice.outDTO.OrderOutDTO;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.serviceImpl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderKafkaProducer orderKafkaProducer;

    private Order order;
    private OrderInDTO orderInDTO;
    private OrderUpdateInDTO orderUpdateInDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        orderInDTO = new OrderInDTO();
        orderInDTO.setCustomerEmail("customer@example.com");
        orderInDTO.setProduct("ProductA");
        orderInDTO.setQuantity(2);

        orderUpdateInDTO = new OrderUpdateInDTO();
        orderUpdateInDTO.setStatus("Processing");

        order = new Order();
        order.setId(1L);
        order.setCustomerEmail(orderInDTO.getCustomerEmail());
        order.setProduct(orderInDTO.getProduct());
        order.setQuantity(orderInDTO.getQuantity());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Ordered");
    }

    @Test
    void testCreateOrder_Success() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderOutDTO result = orderService.createOrder(orderInDTO);

        assertNotNull(result);
        assertEquals(order.getCustomerEmail(), result.getCustomerEmail());
        verify(orderKafkaProducer, times(1)).sendOrderCreatedEvent(any());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testDeleteOrder_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).deleteById(1L);

        assertDoesNotThrow(() -> orderService.deleteOrder(1L));

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOrder_InvalidId() {
        ResourceInvalidException ex = assertThrows(ResourceInvalidException.class, () -> orderService.deleteOrder(-1L));
        assertEquals("Order ID must be a positive number", ex.getMessage());
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(1L));
        assertEquals("Order not found with ID: 1", ex.getMessage());
    }

    @Test
    void testGetOrderById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderOutDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_InvalidId() {
        ResourceInvalidException ex = assertThrows(ResourceInvalidException.class, () -> orderService.getOrderById(0L));
        assertEquals("Order ID must be a positive number", ex.getMessage());
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
        assertEquals("Order not found with ID: 1", ex.getMessage());
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(order, order);
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderOutDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testUpdateOrderStatus_Success_ValidTransitions() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Ordered -> Processing
        orderUpdateInDTO.setStatus("Processing");
        OrderOutDTO updated1 = orderService.updateOrderStatus(1L, orderUpdateInDTO);
        assertEquals("Processing", updated1.getStatus());

        // Processing -> Completed
        order.setStatus("Processing");
        orderUpdateInDTO.setStatus("Completed");
        OrderOutDTO updated2 = orderService.updateOrderStatus(1L, orderUpdateInDTO);
        assertEquals("Completed", updated2.getStatus());

        // Ordered -> Completed
        order.setStatus("Ordered");
        orderUpdateInDTO.setStatus("Completed");
        OrderOutDTO updated3 = orderService.updateOrderStatus(1L, orderUpdateInDTO);
        assertEquals("Completed", updated3.getStatus());

        verify(orderRepository, times(3)).findById(1L);
        verify(orderRepository, times(3)).save(any(Order.class));
    }

    @Test
    void testUpdateOrderStatus_InvalidId() {
        ResourceInvalidException ex = assertThrows(ResourceInvalidException.class,
                () -> orderService.updateOrderStatus(0L, orderUpdateInDTO));
        assertEquals("Order ID must be a positive number", ex.getMessage());
    }

    @Test
    void testUpdateOrderStatus_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> orderService.updateOrderStatus(1L, orderUpdateInDTO));
        assertEquals("Order not found with ID: 1", ex.getMessage());
    }

    @Test
    void testUpdateOrderStatus_InvalidTransition() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderUpdateInDTO.setStatus("Ordered");  // same status
        ResourceInvalidException ex1 = assertThrows(ResourceInvalidException.class,
                () -> orderService.updateOrderStatus(1L, orderUpdateInDTO));
        assertTrue(ex1.getMessage().contains("Invalid status transition"));

        orderUpdateInDTO.setStatus("Ordered");
        order.setStatus("Completed"); // Completed -> Ordered not allowed
        ResourceInvalidException ex2 = assertThrows(ResourceInvalidException.class,
                () -> orderService.updateOrderStatus(1L, orderUpdateInDTO));
        assertTrue(ex2.getMessage().contains("Invalid status transition"));
    }
}
