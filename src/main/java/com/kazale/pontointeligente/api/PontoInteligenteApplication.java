package com.kazale.pontointeligente.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PontoInteligenteApplication {
	
	@Value("${spring.datasource.url}")
	private String parametro;

	public static void main(String[] args) {
		SpringApplication.run(PontoInteligenteApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> System.out.println("## Parâmetro: " + this.parametro);
	}
}
