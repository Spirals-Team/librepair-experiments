package com.pufose.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles("mysql")
public class ServerApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		
	}
	
	
}
