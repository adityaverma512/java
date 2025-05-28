package com.example.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuration class for customizing Jackson's serialization and deserialization behavior
 * for Java 8 date and time types, specifically {@link LocalDateTime}.
 */
@Configuration
public class JacksonConfig {

    /**
     * The formatter used to parse and format {@link LocalDateTime} values.
     * Pattern: {@code yyyy-MM-dd'T'HH:mm:ss.SSS}
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    /**
     * Configures a customized {@link ObjectMapper} bean for Spring's HTTP message conversion.
     * <p>
     * This configuration:
     * <ul>
     *   <li>Registers the {@link JavaTimeModule} to handle Java 8 date/time types</li>
     *   <li>Adds a serializer and deserializer for {@link LocalDateTime} using the specified format</li>
     *   <li>Disables timestamp-based date serialization to ensure ISO 8601 format</li>
     * </ul>
     *
     * @return a customized {@link ObjectMapper} instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));

        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }
}
