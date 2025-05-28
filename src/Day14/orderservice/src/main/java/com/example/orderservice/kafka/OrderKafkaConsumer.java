package com.example.orderservice.kafka;

import com.example.orderservice.feignClient.NotificationServiceFeignClient;
import com.example.orderservice.inDTO.EmailInDTO;
import com.example.orderservice.outDTO.EmailOutDTO;
import com.example.orderservice.outDTO.OrderOutDTO;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer service for processing order events.
 * Consumes order events from Kafka topic and sends confirmation emails via Notification Service.
 */
@Service
public class OrderKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderKafkaConsumer.class);

    @Autowired
    private NotificationServiceFeignClient notificationServiceFeignClient;

    /**
     * Consumes order events from Kafka topic "order-events".
     * Sends a confirmation email to the customer using NotificationServiceFeignClient.
     *
     * @param orderOutDTO the order event payload containing order details
     * @throws KafkaException if any error occurs during processing or sending email
     */
    @KafkaListener(topics = "order-events", groupId = "notification-service-group")
    public void consumeOrderEvent(OrderOutDTO orderOutDTO) {
        try {
            logger.info("Received order event: {}", orderOutDTO);
            logger.info("Sending confirmation email to: {}", orderOutDTO.getCustomerEmail());

            EmailInDTO emailInDTO = new EmailInDTO();
            emailInDTO.setTo(orderOutDTO.getCustomerEmail());
            emailInDTO.setSubject("Order Confirmation Mail");
            emailInDTO.setBody("Order details: " + orderOutDTO);

            EmailOutDTO emailOutDTO = notificationServiceFeignClient.sendEmail(emailInDTO);
            logger.info("Response from notification service: {}", emailOutDTO);
            logger.debug("Order details sent in email: {}", orderOutDTO);

        } catch (Exception e) {
            logger.error("Failed to process order event: {}", e.getMessage(), e);
            throw new KafkaException("Error occurred with messaging service", e);
        }
    }
}
