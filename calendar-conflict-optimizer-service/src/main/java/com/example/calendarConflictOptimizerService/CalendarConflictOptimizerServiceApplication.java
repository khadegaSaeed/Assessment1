package com.example.calendarConflictOptimizerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CalendarConflictOptimizerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarConflictOptimizerServiceApplication.class, args);
	}
}
