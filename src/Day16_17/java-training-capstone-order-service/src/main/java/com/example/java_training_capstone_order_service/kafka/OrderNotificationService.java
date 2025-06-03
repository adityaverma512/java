package com.example.java_training_capstone_order_service.kafka;

import com.example.java_training_capstone_order_service.config.KafkaTopicConfig;
import com.example.java_training_capstone_order_service.outDTO.OrderNotificationOutDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for publishing order-related notification events to a Kafka topic.
 * <p>
 * This class uses {@link KafkaTemplate} to send messages of type {@link OrderNotificationOutDTO}
 * to the topic defined in {@link KafkaTopicConfig#ORDER_NOTIFICATION_TOPIC}.
 * </p>
 */
@Service
public class OrderNotificationService {

    private final KafkaTemplate<String, OrderNotificationOutDTO> kafkaTemplate;

    /**
     * Constructs the {@code OrderNotificationService} with the specified Kafka template.
     *
     * @param kafkaTemplate the Kafka template used to send order notification events.
     */
    public OrderNotificationService(KafkaTemplate<String, OrderNotificationOutDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends an order notification message to the Kafka topic.
     * <p>
     * The message key is the order ID, which helps ensure ordering within partitions,
     * and the value is the {@link OrderNotificationOutDTO} payload.
     * </p>
     *
     * @param notification the order notification data to be sent.
     */
    public void sendOrderNotification(OrderNotificationOutDTO notification) {
        kafkaTemplate.send(KafkaTopicConfig.ORDER_NOTIFICATION_TOPIC, notification.getOrderId(), notification);
    }
}
