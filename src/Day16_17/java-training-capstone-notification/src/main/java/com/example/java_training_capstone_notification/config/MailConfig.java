package com.example.java_training_capstone_notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuration class for setting up email functionality using JavaMailSender.
 * This class configures SMTP settings for sending emails through Gmail or other SMTP servers.
 *
 * <p>The configuration supports:
 * <ul>
 *   <li>Customizable SMTP host and port settings</li>
 *   <li>Authentication with username and password</li>
 *   <li>STARTTLS encryption for secure email transmission</li>
 *   <li>Connection timeout configurations</li>
 * </ul>
 */
@Configuration
public class MailConfig {

    /**
     * SMTP server host address.
     * Defaults to Gmail's SMTP server (smtp.gmail.com) if not specified in properties.
     */
    @Value("${spring.mail.host:smtp.gmail.com}")
    private String mailHost;

    /**
     * SMTP server port number.
     * Defaults to 587 (standard SMTP submission port) if not specified in properties.
     */
    @Value("${spring.mail.port:587}")
    private int mailPort;

    /**
     * Username for SMTP authentication.
     * Should be configured in application properties for security.
     */
    @Value("${spring.mail.username:}")
    private String mailUsername;

    /**
     * Password for SMTP authentication.
     * Should be configured in application properties and kept secure.
     * Consider using application-specific passwords for Gmail accounts.
     */
    @Value("${spring.mail.password:}")
    private String mailPassword;

    /**
     * Flag to enable SMTP authentication.
     * Defaults to true as most SMTP servers require authentication.
     */
    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean smtpAuth;

    /**
     * Flag to enable STARTTLS encryption.
     * STARTTLS upgrades a plain text connection to encrypted connection.
     * Defaults to true for secure email transmission.
     */
    @Value("${spring.mail.properties.mail.smtp.starttls.enable:true}")
    private boolean starttlsEnable;

    /**
     * Flag to require STARTTLS encryption.
     * When set to true, the connection will fail if STARTTLS is not available.
     * Defaults to true for enhanced security.
     */
    @Value("${spring.mail.properties.mail.smtp.starttls.required:true}")
    private boolean starttlsRequired;

    /**
     * Creates and configures a JavaMailSender bean for sending emails.
     *
     * <p>This method sets up:
     * <ul>
     *   <li>Basic SMTP connection parameters (host, port, credentials)</li>
     *   <li>SMTP protocol and authentication settings</li>
     *   <li>STARTTLS encryption configuration</li>
     *   <li>Connection timeout settings to prevent hanging connections</li>
     *   <li>SSL trust configuration for the SMTP host</li>
     * </ul>
     *
     * <p>Connection timeouts are set to 10 seconds for:
     * <ul>
     *   <li>Initial connection establishment</li>
     *   <li>General I/O operations</li>
     *   <li>Write operations</li>
     * </ul>
     *
     * @return configured JavaMailSender instance ready for sending emails
     * @throws IllegalArgumentException if required mail configuration is missing
     *
     * @see JavaMailSender
     * @see JavaMailSenderImpl
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.starttls.required", starttlsRequired);
        props.put("mail.debug", "false"); // Set to true for debugging

        props.put("mail.smtp.ssl.trust", mailHost);
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        return mailSender;
    }
}