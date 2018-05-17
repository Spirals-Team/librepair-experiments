package com.dutra.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeuPrimeiroProjetoApplication {


//	@Autowired
//	private EmpresaRepository empresaRepository;

//	@Value("${paginacao.qtd_por_pagina}")
//	private int qtdPaginas;

	public static void main(String[] args) {
		SpringApplication.run(MeuPrimeiroProjetoApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			String senhaEnconded = SenhaUtils.gerarBcrypt("123456");
			System.out.println("Senha enconded: " + senhaEnconded);
			senhaEnconded = SenhaUtils.gerarBcrypt("123456");
			System.out.println("Senha enconded novamente: " + senhaEnconded);
			System.out.println("Senha válida: " + SenhaUtils.senhaValida("123456", senhaEnconded));

			System.out.println("###Quantidade de elementos por página = " + this.qtdPaginas);

			Empresa empresa = new Empresa();
			empresa.setRazaoSocial("Dutra IT");
			empresa.setCnpj("22253869000115");

			this.empresaRepository.save(empresa);

			List<Empresa> empresas = empresaRepository.findAll();
			empresas.forEach(System.out::println);

			empresa = empresaRepository.findTopById(1L);
			System.out.println("Empresa por ID:" + empresa);

			empresa.setRazaoSocial("Dutra IT Web");
			this.empresaRepository.save(empresa);

			empresa = this.empresaRepository.findByCnpj("22253869000115");
			System.out.println("Empresa por CNPJ: " + empresa);

			this.empresaRepository.deleteById(1L);
			empresas = this.empresaRepository.findAll();
			System.out.println("Empresas: " + empresas.size());
		};
	}*/
}
