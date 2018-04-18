package br.com.tsp.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.tsp.api.utils.SenhaUtils;

@SpringBootApplication
public class MeuPrimeiroProjetoApplication {

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	public static void main(String[] args) {
		SpringApplication.run(MeuPrimeiroProjetoApplication.class, args);
		System.out.println("Meu primeiro projeto"); 
	}
	
	@Bean
	public CommandLineRunner commandLineRunnter() {
		return args -> {
			System.out.println("### Quantidade de elementos por página = " + this.qtdPorPagina);
			
			String senhaEncoded1 = SenhaUtils.gerarBcrypt("123456");
			System.out.println("senhaEncoded1 = " + senhaEncoded1);
			
			String senhaEncoded2 = SenhaUtils.gerarBcrypt("123456");
			System.out.println("senhaEncoded2 = " + senhaEncoded2);
			
			System.out.println("teste 1, válido = " + SenhaUtils.isSenhaValida("123456", senhaEncoded1));
			System.out.println("teste 2, válido = " + SenhaUtils.isSenhaValida("123456", senhaEncoded2));
		};	
	}
}