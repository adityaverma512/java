package com.example.notificationservice.ServiceImplTest;

import com.example.notificationservice.exception.EmailServiceException;
import com.example.notificationservice.inDTO.EmailInDTO;
import com.example.notificationservice.outDTO.EmailOutDTO;
import com.example.notificationservice.service.EmailService;
import com.example.notificationservice.serviceImpl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmailSuccess() {
        // Arrange
        EmailInDTO input = new EmailInDTO("to@example.com", "Test Subject", "Test Body");

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        // Act
        EmailOutDTO result = emailService.sendEmail(input);

        // Assert
        assertNotNull(result);
        assertEquals("Mail sent successfully", result.getMessage());

        verify(javaMailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage captured = messageCaptor.getValue();
        assertEquals("to@example.com", captured.getTo()[0]);
        assertEquals("Test Subject", captured.getSubject());
        assertEquals("Test Body", captured.getText());
        assertEquals("orderservice@gmail.com", captured.getReplyTo());
    }

    @Test
    void testSendEmailThrowsException() {
        // Arrange
        EmailInDTO input = new EmailInDTO("to@example.com", "Test Subject", "Test Body");

        doThrow(new RuntimeException("SMTP failure")).when(javaMailSender).send(any(SimpleMailMessage.class));

        // Act & Assert
        EmailServiceException exception = assertThrows(EmailServiceException.class, () -> {
            emailService.sendEmail(input);
        });

        assertEquals("Failed to send email", exception.getMessage());

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}