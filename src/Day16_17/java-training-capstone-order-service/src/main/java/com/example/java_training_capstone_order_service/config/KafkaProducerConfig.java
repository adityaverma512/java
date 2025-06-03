package com.example.java_training_capstone_order_service.config;

import com.example.java_training_capstone_order_service.outDTO.OrderNotificationOutDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka producer settings for the Order Service.
 * <p>
 * This configures the serialization logic and connection details required to publish
 * {@link OrderNotificationOutDTO} messages to Kafka topics.
 * </p>
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Defines the producer factory that Kafka will use to produce messages.
     * <p>
     * This factory configures:
     * <ul>
     *     <li>Kafka bootstrap server address</li>
     *     <li>Key serializer (as {@link StringSerializer})</li>
     *     <li>Value serializer (as {@link JsonSerializer})</li>
     * </ul>
     * </p>
     *
     * @return a {@link ProducerFactory} that produces messages with key as {@code String}
     *         and value as {@link OrderNotificationOutDTO}.
     */
    @Bean
    public ProducerFactory<String, OrderNotificationOutDTO> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Defines a {@link KafkaTemplate} bean to send messages to Kafka topics.
     * <p>
     * This template provides high-level APIs to publish {@link OrderNotificationOutDTO}
     * messages using the configured {@link ProducerFactory}.
     * </p>
     *
     * @return a {@link KafkaTemplate} for sending order notification messages.
     */
    @Bean
    public KafkaTemplate<String, OrderNotificationOutDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
