package com.fitness.fitness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FitnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessApplication.class, args);
	}
	

}
