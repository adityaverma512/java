package com.example.orderservice.inDTOTest;


import com.example.orderservice.inDTO.EmailInDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmailInDTOValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private EmailInDTO createDTO(String to, String subject, String body) {
        return new EmailInDTO(to, subject, body);
    }

    @Test
    void testValidDTO() {
        EmailInDTO dto = createDTO("user@gmail.com", "Order Update", "Your order is ready");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    // ---------- "to" field ----------

    @Test
    void testTo_NullEmail() {
        EmailInDTO dto = createDTO(null, "Test", "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("to"));
    }

    @Test
    void testTo_EmptyEmail() {
        EmailInDTO dto = createDTO("", "Test", "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("to"));
    }

    @Test
    void testTo_InvalidEmailFormat() {
        EmailInDTO dto = createDTO("user@@gmail.com", "Test", "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("to") &&
                        v.getMessage().contains("valid")
        );
    }

    @Test
    void testTo_NonGmailDomain() {
        EmailInDTO dto = createDTO("user@yahoo.com", "Test", "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("to") &&
                        v.getMessage().contains("Only Gmail addresses are accepted")
        );
    }

    // ---------- "subject" field ----------

    @Test
    void testSubject_Null() {
        EmailInDTO dto = createDTO("user@gmail.com", null, "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("subject"));
    }

    @Test
    void testSubject_TooShort() {
        EmailInDTO dto = createDTO("user@gmail.com", "A", "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("subject"));
    }

    @Test
    void testSubject_TooLong() {
        EmailInDTO dto = createDTO("user@gmail.com", "This subject line is way too long to be valid", "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("subject"));
    }

    @Test
    void testSubject_InvalidCharacters() {
        EmailInDTO dto = createDTO("user@gmail.com", "Hello123!", "Body");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("subject"));
    }

    // ---------- "body" field ----------

    @Test
    void testBody_Null() {
        EmailInDTO dto = createDTO("user@gmail.com", "Test", null);
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("body"));
    }

    @Test
    void testBody_TooShort() {
        EmailInDTO dto = createDTO("user@gmail.com", "Test", "B");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("body"));
    }

    @Test
    void testBody_TooLong() {
        EmailInDTO dto = createDTO("user@gmail.com", "Test", "This body text is way too long for the validation to pass");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("body"));
    }

    @Test
    void testBody_InvalidCharacters() {
        EmailInDTO dto = createDTO("user@gmail.com", "Test", "Hello123!");
        Set<ConstraintViolation<EmailInDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("body"));
    }
}
