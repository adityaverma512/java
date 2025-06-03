package com.example.java_training_capstone_notification.kafka;

import com.example.java_training_capstone_notification.config.KafkaTopicConfig;
import com.example.java_training_capstone_notification.inDTO.OrderNotificationInDTO;
import com.example.java_training_capstone_notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka listener service for handling incoming order notification events.
 * <p>
 * This service listens to the Kafka topic configured in {@link KafkaTopicConfig#ORDER_NOTIFICATION_TOPIC}
 * and delegates processing to the appropriate methods in {@link NotificationService}
 * based on the type of event received (e.g., ORDER_CREATED, ORDER_STATUS_UPDATED).
 * </p>
 */
@Service
public class OrderNotificationListener {

    @Autowired
    NotificationService notificationService;

    /**
     * Listens to messages from the Kafka topic specified in {@link KafkaTopicConfig#ORDER_NOTIFICATION_TOPIC}.
     * Based on the event type in {@link OrderNotificationInDTO}, it delegates to the appropriate
     * method in {@link NotificationService}.
     *
     * @param orderNotification the order notification DTO received from Kafka.
     */
    @KafkaListener(topics = KafkaTopicConfig.ORDER_NOTIFICATION_TOPIC, groupId = "notification-service-group")
    public void listen(OrderNotificationInDTO orderNotification) {
        System.out.println("Received Order Notification: " + orderNotification);
        String eventType = orderNotification.getEventType();
        if (eventType.equals("ORDER_CREATED")) {
            notificationService.sendOrderCreatedNotification(orderNotification);
        } else if (eventType.equals("ORDER_STATUS_UPDATED")) {
            notificationService.sendOrderStatusUpdatedNotification(orderNotification);
        }
    }
}
