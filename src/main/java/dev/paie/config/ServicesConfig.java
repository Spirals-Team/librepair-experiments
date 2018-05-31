package dev.paie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.paie.entite.BulletinSalaire;

@Configuration
@ComponentScan("dev.paie.service, dev.paie.util")
@ImportResource("classpath:jdd-config.xml")
@Import({ JpaConfig.class, DataSourceMySQLConfig.class })

@EnableJpaRepositories("dev.paie.repository") // Configuration à ajouter.
public class ServicesConfig {

	@Bean
	public BulletinSalaire bulletinSalaire() {
		return new BulletinSalaire();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
