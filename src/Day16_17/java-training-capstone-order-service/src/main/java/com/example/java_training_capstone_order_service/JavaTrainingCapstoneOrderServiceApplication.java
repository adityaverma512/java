package com.example.java_training_capstone_order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class JavaTrainingCapstoneOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTrainingCapstoneOrderServiceApplication.class, args);
	}

}
