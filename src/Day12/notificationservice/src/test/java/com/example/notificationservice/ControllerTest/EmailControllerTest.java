package com.example.notificationservice.ControllerTest;

import com.example.notificationservice.controller.EmailController;
import com.example.notificationservice.inDTO.EmailInDTO;
import com.example.notificationservice.outDTO.EmailOutDTO;
import com.example.notificationservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmailController.class)
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSendEmailSuccess() throws Exception {
        EmailInDTO input = new EmailInDTO("test@example.com", "Subject", "Body");
        EmailOutDTO output = new EmailOutDTO();
        output.setMessage("Email sent successfully");

        Mockito.when(emailService.sendEmail(any(EmailInDTO.class))).thenReturn(output);

        mockMvc.perform(post("/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Email sent successfully"));
    }

    @Test
    void testSendEmailServiceException() throws Exception {
        EmailInDTO input = new EmailInDTO("test@example.com", "Subject", "Body");

        Mockito.when(emailService.sendEmail(any(EmailInDTO.class)))
                .thenThrow(new com.example.notificationservice.exception.EmailServiceException("Service unavailable"));

        mockMvc.perform(post("/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Service unavailable"));
    }
}
