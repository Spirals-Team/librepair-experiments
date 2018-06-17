package com.les.brouilles.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LesBrouillesPlannerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(LesBrouillesPlannerApplication.class, args);
	}
}
