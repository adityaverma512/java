package com.example.orderservice.config;

import com.example.orderservice.outDTO.OrderOutDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka producer and consumer components
 * used in the Order Service.
 */
@Configuration
public class KafkaConfig {

    /**
     * Kafka bootstrap server address (host:port), injected from application properties.
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Configures the Kafka producer factory for sending messages with key of type {@link String}
     * and value of type {@link OrderOutDTO}.
     *
     * @return a configured {@link ProducerFactory} bean
     */
    @Bean
    public ProducerFactory<String, OrderOutDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Kafka template used for sending messages to Kafka topics.
     *
     * @return a configured {@link KafkaTemplate} bean
     */
    @Bean
    public KafkaTemplate<String, OrderOutDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Configures the Kafka consumer factory to receive messages with key of type {@link String}
     * and value of type {@link OrderOutDTO}.
     *
     * @return a configured {@link ConsumerFactory} bean
     */
    @Bean
    public ConsumerFactory<String, OrderOutDTO> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-service-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.orderservice.outDTO");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderOutDTO.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Configures the Kafka listener container factory, which enables
     * concurrent processing of Kafka messages.
     *
     * @return a configured {@link ConcurrentKafkaListenerContainerFactory} bean
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderOutDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderOutDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
