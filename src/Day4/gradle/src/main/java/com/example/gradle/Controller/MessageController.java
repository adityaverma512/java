package com.example.gradle.Controller;

import com.example.gradle.Service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private IMessageService iMessageService;

    @Value("${app.sender}")
    private String sender;

    @PostMapping
    public String sendMessage(@RequestParam String to, @RequestParam String message)
    {
        iMessageService.sendMessage(to,message+ " -from "+sender);
        return "Message Sent";
    }
}
