package com.kairos.pontointeligente.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PontoInteligenteApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(PontoInteligenteApplicationTests.class, args);
	}
}
