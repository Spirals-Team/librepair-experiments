package com.friendtrip.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.friendtrip.api.entities.Empresa;
import com.friendtrip.api.repositories.EmpresaRepository;

@SpringBootApplication
public class FriendTripApiApplication {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(FriendTripApiApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner comLineRunner() {
		return args ->  {
			Empresa empresa = new Empresa();
			empresa.setRazaoSocial("Friend");
			empresa.setCnpj("1234567890");
			
			this.empresaRepository.save(empresa); 
			
			List<Empresa> empresas = empresaRepository.findAll();
			empresas.forEach(System.out::println);
			
			Empresa empresadb =	empresas.get(0);
			empresadb.setRazaoSocial("Friend Trip");
			
			this.empresaRepository.save(empresadb);
			
			Empresa empresaCnpj = empresaRepository.findByCnpj("1234567890");
			System.out.println(empresaCnpj);
			
			
		};
	}
}
