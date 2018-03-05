package com.tiagogouvea.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootExampleApplication {
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtPaginas;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootExampleApplication.class, args);
		System.out.println("Teste Travis");
	}
	
	@Bean
	public CommandLineRunner teste() {
		
		return args -> {
			System.out.println("Quantidade = " + this.qtPaginas); 
		};
	}
}
