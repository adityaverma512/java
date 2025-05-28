//package com.example.notificationservice.ExceptionTest;
//
//import com.example.notificationservice.Exception.GlobalExceptionHandler;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GlobalExceptionHandlerTest {
//
//    private GlobalExceptionHandler globalExceptionHandler;
//
//    @BeforeEach
//    void setUp() {
//        globalExceptionHandler = new GlobalExceptionHandler();
//    }
//
//    @Test
//    void testHandleEmailServiceException_returnsServiceUnavailableStatus() {
//        // Arrange
//        String errorMessage = "Failed to send email";
//        com.example.notificationservice.exception.EmailServiceException ex = new com.example.notificationservice.exception.EmailServiceException(errorMessage);
//
//        // Act
//        ResponseEntity<com.example.notificationservice.exception.ErrorResponse> response = globalExceptionHandler.handleEmailServiceException(ex);
//        com.example.notificationservice.exception.ErrorResponse body = response.getBody();
//
//        // Assert
//        assertNotNull(body);
//        assertEquals(503, response.getStatusCodeValue());
//        assertEquals(503, body.getStatus());
//        assertEquals(errorMessage, body.getMessage());
//        assertNotNull(body.getTimestamp());
//
//        // Ensure timestamp is not in the future
//        assertTrue(body.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
//    }
//}
