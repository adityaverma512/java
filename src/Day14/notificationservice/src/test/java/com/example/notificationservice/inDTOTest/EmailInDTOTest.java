package com.example.notificationservice.inDTOTest;

import com.example.notificationservice.inDTO.EmailInDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailInDTOTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        EmailInDTO dto = new EmailInDTO();
        dto.setTo("recipient@example.com");
        dto.setSubject("Test Subject");
        dto.setBody("Test Body");

        assertEquals("recipient@example.com", dto.getTo());
        assertEquals("Test Subject", dto.getSubject());
        assertEquals("Test Body", dto.getBody());
    }

    @Test
    void testAllArgsConstructor() {
        EmailInDTO dto = new EmailInDTO("to@example.com", "Hello", "Body here");

        assertEquals("to@example.com", dto.getTo());
        assertEquals("Hello", dto.getSubject());
        assertEquals("Body here", dto.getBody());
    }

    @Test
    void testEqualsAndHashCode() {
        EmailInDTO dto1 = new EmailInDTO("a@b.com", "Sub", "Body");
        EmailInDTO dto2 = new EmailInDTO("a@b.com", "Sub", "Body");
        EmailInDTO dto3 = new EmailInDTO("x@y.com", "Different", "Another");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        EmailInDTO dto = new EmailInDTO("to@domain.com", "Subject", "Body content");
        String expected = "EmailInDTO{to='to@domain.com', subject='Subject', body='Body content'}";
        assertEquals(expected, dto.toString());
    }
}