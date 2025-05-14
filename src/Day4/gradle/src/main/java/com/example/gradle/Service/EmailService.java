package com.example.gradle.Service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class EmailService implements IMessageService{
    @Override
    public void sendMessage(String to, String message) {
        System.out.println("This is Email service to : "+to+"With message : "+ message);
    }
}
