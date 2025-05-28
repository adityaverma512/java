package com.example.notificationservice.service;

import com.example.notificationservice.inDTO.EmailInDTO;
import com.example.notificationservice.outDTO.EmailOutDTO;

public interface EmailService {
    public EmailOutDTO sendEmail(EmailInDTO emailInDTO);
}
