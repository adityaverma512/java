package com.example.orderservice.kafka;

import com.example.orderservice.outDTO.OrderOutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer service for sending order events.
 * Publishes order-created events to the configured Kafka topic.
 */
@Service
public class OrderKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(OrderKafkaProducer.class);

    private static final String TOPIC = "order-events";

    private final KafkaTemplate<String, OrderOutDTO> kafkaTemplate;

    /**
     * Constructs the Kafka producer with the injected KafkaTemplate.
     *
     * @param kafkaTemplate the KafkaTemplate to send messages
     */
    public OrderKafkaProducer(KafkaTemplate<String, OrderOutDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends an order-created event to the Kafka topic.
     *
     * @param orderOutDTO the order data transfer object to send
     */
    public void sendOrderCreatedEvent(OrderOutDTO orderOutDTO) {
        logger.info("Sending order event: {}", orderOutDTO);
        kafkaTemplate.send(TOPIC, orderOutDTO);
    }
}
