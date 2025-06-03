package com.example.java_training_capstone_notification.config;

import com.example.java_training_capstone_notification.inDTO.OrderNotificationInDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka consumer configuration class for setting up deserialization and consumer factory
 * for handling messages of type {@link OrderNotificationInDTO}.
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * Configures and creates a {@link ConsumerFactory} for consuming messages with a key of type {@code String}
     * and a value of type {@link OrderNotificationInDTO}.
     * <p>
     * Uses {@link JsonDeserializer} to deserialize incoming JSON payloads into Java objects.
     * </p>
     *
     * @return a configured {@link ConsumerFactory} for order notification messages.
     */
    @Bean
    public ConsumerFactory<String, OrderNotificationInDTO> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.example.java_training_capstone_notification.inDTO.OrderNotificationInDTO");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * Creates a {@link ConcurrentKafkaListenerContainerFactory} using the custom consumer factory.
     * <p>
     * This factory is responsible for managing Kafka listener containers that listen
     * to topics with messages of type {@link OrderNotificationInDTO}.
     * </p>
     *
     * @return a configured {@link ConcurrentKafkaListenerContainerFactory}.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderNotificationInDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderNotificationInDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
