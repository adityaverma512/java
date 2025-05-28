package com.example.notificationservice.controller;

import com.example.notificationservice.inDTO.EmailInDTO;
import com.example.notificationservice.outDTO.EmailOutDTO;
import com.example.notificationservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling email-related requests.
 * Provides an endpoint to send emails.
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    /**
     * Sends an email based on the provided email data.
     *
     * @param emailInDTO the DTO containing email details such as recipient, subject, and body
     * @return an EmailOutDTO containing the response message after sending the email
     */
    @PostMapping
    public EmailOutDTO sendEmail(@RequestBody EmailInDTO emailInDTO) {
        logger.info("Received request to send email: {}", emailInDTO);
        EmailOutDTO response = emailService.sendEmail(emailInDTO);
        logger.info("Email send response: {}", response);
        return response;
    }
}
