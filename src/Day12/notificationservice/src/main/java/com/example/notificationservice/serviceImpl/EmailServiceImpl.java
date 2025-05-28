package com.example.notificationservice.serviceImpl;

import com.example.notificationservice.exception.EmailServiceException;
import com.example.notificationservice.inDTO.EmailInDTO;
import com.example.notificationservice.outDTO.EmailOutDTO;
import com.example.notificationservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of EmailService that sends emails using Spring's JavaMailSender.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Sends an email using the given EmailInDTO details.
     *
     * @param emailInDTO the DTO containing email details such as recipient, subject, and body
     * @return an EmailOutDTO containing the result message of the send operation
     * @throws EmailServiceException if sending the email fails
     */
    @Override
    public EmailOutDTO sendEmail(EmailInDTO emailInDTO) {
        EmailOutDTO emailOutDTO = new EmailOutDTO();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setReplyTo("orderservice@gmail.com");
            message.setTo(emailInDTO.getTo());
            message.setSubject(emailInDTO.getSubject());
            message.setText(emailInDTO.getBody());

            javaMailSender.send(message);
            logger.info("Email sent successfully to {}", emailInDTO.getTo());

            emailOutDTO.setMessage("Mail sent successfully");
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", emailInDTO.getTo(), e.getMessage(), e);
            throw new EmailServiceException("Failed to send email");
        }

        return emailOutDTO;
    }
}
