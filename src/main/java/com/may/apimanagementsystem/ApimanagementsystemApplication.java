package com.may.apimanagementsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.may.apimanagementsystem.dao")
public class ApimanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApimanagementsystemApplication.class, args);
	}

}
