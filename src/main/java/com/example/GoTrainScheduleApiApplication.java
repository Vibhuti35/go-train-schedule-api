package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GoTrainScheduleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoTrainScheduleApiApplication.class, args);
	}
}
