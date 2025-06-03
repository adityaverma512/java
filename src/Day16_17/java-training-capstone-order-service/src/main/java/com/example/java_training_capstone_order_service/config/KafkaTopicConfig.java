package com.example.java_training_capstone_order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for defining Kafka topics used by the Order Service.
 * <p>
 * This class sets up the {@code order-notifications} topic, which is used
 * to publish order-related events to be consumed by other services (e.g., Notification Service).
 * </p>
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * The name of the Kafka topic used for sending order notification events.
     */
    public static final String ORDER_NOTIFICATION_TOPIC = "order-notifications";

    /**
     * Defines a Kafka topic bean named {@code order-notifications}.
     * <p>
     * The topic is configured with:
     * <ul>
     *     <li>1 partition – suitable for development or simple ordering requirements</li>
     *     <li>1 replica – ensures minimal replication (not highly fault-tolerant)</li>
     * </ul>
     * </p>
     *
     * @return a {@link NewTopic} object representing the topic configuration.
     */
    @Bean
    public NewTopic orderNotificationTopic() {
        return TopicBuilder.name(ORDER_NOTIFICATION_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
