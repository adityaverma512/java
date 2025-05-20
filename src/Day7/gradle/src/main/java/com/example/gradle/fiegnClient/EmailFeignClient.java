package com.example.gradle.fiegnClient;
import com.example.gradle.DTO.EmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gradle", url = "http://localhost:8081" )
public interface EmailFeignClient {

    @PostMapping("/api/email/send")
    String sendEmail(@RequestBody EmailDto emailRequest);
}

