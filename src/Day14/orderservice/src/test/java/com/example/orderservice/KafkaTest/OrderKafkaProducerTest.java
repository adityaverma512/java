package com.example.orderservice.KafkaTest;

import com.example.orderservice.kafka.OrderKafkaProducer;
import com.example.orderservice.outDTO.OrderOutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderKafkaProducerTest {

    @Mock
    private KafkaTemplate<String, OrderOutDTO> kafkaTemplate;

    private OrderKafkaProducer orderKafkaProducer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderKafkaProducer = new OrderKafkaProducer(kafkaTemplate);
    }

    @Test
    void testSendOrderCreatedEvent() {
        OrderOutDTO dto = new OrderOutDTO();
        dto.setId(1L);

        when(kafkaTemplate.send(anyString(), any(OrderOutDTO.class))).thenReturn(null); // We don't care about future here

        orderKafkaProducer.sendOrderCreatedEvent(dto);

        verify(kafkaTemplate, times(1)).send("order-events", dto);
    }
}
