package com.example.orderservice.KafkaTest;

import com.example.orderservice.feignClient.NotificationServiceFeignClient;
import com.example.orderservice.inDTO.EmailInDTO;
import com.example.orderservice.kafka.OrderKafkaConsumer;
import com.example.orderservice.outDTO.EmailOutDTO;
import com.example.orderservice.outDTO.OrderOutDTO;
import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderKafkaConsumerTest {

    @Mock
    private NotificationServiceFeignClient notificationServiceFeignClient;

    private OrderKafkaConsumer orderKafkaConsumer;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        orderKafkaConsumer = new OrderKafkaConsumer();

        // Inject mock into private field using reflection
        Field feignClientField = OrderKafkaConsumer.class.getDeclaredField("notificationServiceFeignClient");
        feignClientField.setAccessible(true);
        feignClientField.set(orderKafkaConsumer, notificationServiceFeignClient);
    }

    @Test
    void testConsumeOrderEvent_Success() {
        // Arrange
        OrderOutDTO orderOutDTO = new OrderOutDTO();
        orderOutDTO.setCustomerEmail("customer@example.com");

        EmailOutDTO emailOutDTO = new EmailOutDTO();
        emailOutDTO.setMessage("Email sent");

        when(notificationServiceFeignClient.sendEmail(any(EmailInDTO.class))).thenReturn(emailOutDTO);

        // Act & Assert
        assertDoesNotThrow(() -> orderKafkaConsumer.consumeOrderEvent(orderOutDTO));

        // Verify Feign client call
        verify(notificationServiceFeignClient, times(1)).sendEmail(any(EmailInDTO.class));
    }

    @Test
    void testConsumeOrderEvent_Exception() {
        // Arrange
        OrderOutDTO orderOutDTO = new OrderOutDTO();
        orderOutDTO.setCustomerEmail("customer@example.com");

        when(notificationServiceFeignClient.sendEmail(any(EmailInDTO.class)))
                .thenThrow(new RuntimeException("Feign client failure"));

        // Act & Assert
        KafkaException ex = assertThrows(KafkaException.class, () -> orderKafkaConsumer.consumeOrderEvent(orderOutDTO));
        assertTrue(ex.getMessage().contains("Error occurred with messaging service"));
    }
}
