package com.example.java_training_capstone_notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for defining Kafka topics used in the notification service.
 * <p>
 * This class declares the Kafka topic required for listening to order-related events,
 * such as order creation and status updates.
 * </p>
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * The name of the Kafka topic for order notifications.
     */
    public static final String ORDER_NOTIFICATION_TOPIC = "order-notifications";

    /**
     * Creates and configures a Kafka topic bean named {@code order-notifications}.
     * <p>
     * This topic is used by the notification microservice to consume order-related events.
     * It is configured with 1 partition and 1 replica, suitable for development or testing environments.
     * </p>
     *
     * @return a {@link NewTopic} bean representing the order notification topic.
     */
    @Bean
    public NewTopic orderNotificationTopic() {
        return TopicBuilder.name(ORDER_NOTIFICATION_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
