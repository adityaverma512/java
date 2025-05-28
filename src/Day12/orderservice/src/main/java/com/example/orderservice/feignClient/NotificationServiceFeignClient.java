package com.example.orderservice.feignClient;

import com.example.orderservice.inDTO.EmailInDTO;
import com.example.orderservice.outDTO.EmailOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationservice", url = "http://localhost:8081/email")
public interface NotificationServiceFeignClient {
    @PostMapping
    public EmailOutDTO sendEmail(@RequestBody EmailInDTO emailInDTO);
}
