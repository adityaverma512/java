package com.example.gradle.Service;

import com.example.gradle.dto.EmailRequestDto;

public interface EmailService {
    void sendEmail(EmailRequestDto request);
}

