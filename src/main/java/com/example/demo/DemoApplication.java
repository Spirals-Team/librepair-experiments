package com.example.demo;

import com.example.demo.security.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.example.demo.services.ExemploService;

@SpringBootApplication
@EnableCaching
public class DemoApplication {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ExemploService exemploService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            /*repositories.deleteAll();

            repositories.save(new Cliente("Alice", 20));
            repositories.save(new Cliente("João", 30));
            repositories.save(new Cliente("Maria", 40));

            System.out.println("Lista todos com o findAll():");
            System.out.println("-------------------------------");
            repositories.findAll().forEach(System.out::println);
            System.out.println();

            System.out.println("Busca um único cliente com o findByName('Alice'):");
            System.out.println("--------------------------------");
            System.out.println(repositories.findByNome("Alice"));
            System.out.println();

            System.out.println("Clientes com idade entre 18 and 35:");
            System.out.println("--------------------------------");
            repositories.findByIdadeBetween(18, 35).forEach(System.out::println);*/

            /*System.out.println("Executando serviço pela primeira vez");
            System.out.println(this.exemploService.exemploCache());

            System.out.println("Executando serviço pela segunda vez, deve obter dados do cache");
            System.out.println(this.exemploService.exemploCache());*/

            /*Usuario usuario = new Usuario();
            usuario.setEmail("usuario@email.com");
            usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
            usuario.setSenha(PasswordUtils.gerarBCrypt("123456"));
            this.usuarioRepository.save(usuario);

            Usuario admin = new Usuario();
            admin.setEmail("admin@email.com");
            admin.setPerfil(PerfilEnum.ROLE_ADMIN);
            admin.setSenha(PasswordUtils   .gerarBCrypt("123456"));
            this.usuarioRepository.save(admin);*/

        };
    }
}
