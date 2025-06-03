package com.example.java_training_capstone_notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class JavaTrainingCapstoneNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTrainingCapstoneNotificationApplication.class, args);
	}

}
