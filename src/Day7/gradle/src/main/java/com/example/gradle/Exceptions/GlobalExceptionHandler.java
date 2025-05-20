package com.example.gradle.Exceptions;

import com.example.gradle.DTO.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(EmployeeNotFoundException ex, HttpServletRequest request) {
        logger.warn("EmployeeNotFoundException: {} at {}", ex.getMessage(), request.getRequestURI());
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobal(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception: {} at {}", ex.getMessage(), request.getRequestURI(), ex);
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidFileFormat(InvalidFileFormatException ex, HttpServletRequest request) {
        logger.warn("InvalidFileFormatException: {} at {}", ex.getMessage(), request.getRequestURI());
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(Exception ex, HttpStatus status, String path) {
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                path
        );
        logger.debug("ErrorResponseDto created: {}", error);
        return new ResponseEntity<>(error, status);
    }
}
