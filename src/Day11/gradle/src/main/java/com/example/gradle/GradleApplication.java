package com.example.gradle;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GradleApplication {
	private static final Logger logger = LoggerFactory.getLogger(GradleApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GradleApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(EntityManagerFactory entityManagerFactory) {
		return args -> {
			SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
			logger.info("SessionFactory initialized: {}", sessionFactory);
		};
	}
}
