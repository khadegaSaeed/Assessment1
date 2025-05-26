package com.example.userPropertyBookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserPropertyBookingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserPropertyBookingServiceApplication.class, args);
	}
}
