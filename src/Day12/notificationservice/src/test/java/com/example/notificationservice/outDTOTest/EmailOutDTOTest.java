package com.example.notificationservice.outDTOTest;

import com.example.notificationservice.outDTO.EmailOutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailOutDTOTest {

    @Test
    void testSetterGetter() {
        EmailOutDTO dto = new EmailOutDTO();
        dto.setMessage("Success");

        assertEquals("Success", dto.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        EmailOutDTO dto1 = new EmailOutDTO();
        dto1.setMessage("Sent");

        EmailOutDTO dto2 = new EmailOutDTO();
        dto2.setMessage("Sent");

        EmailOutDTO dto3 = new EmailOutDTO();
        dto3.setMessage("Failed");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        EmailOutDTO dto = new EmailOutDTO();
        dto.setMessage("Mail sent successfully");
        String expected = "EmailOutDTO{message='Mail sent successfully'}";

        assertEquals(expected, dto.toString());
    }
}

