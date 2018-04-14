package com.prussia.play.spring.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Profile("prod")
@Slf4j
public class DataSourceConfiguration {

	@Bean
	public DataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		DataSourceBuilder factory = DataSourceBuilder.create()
				.driverClassName("org.postgresql.Driver")
				.url(dbUrl)
				.username(username)
				.password(password);
		
		log.warn("datasource info is {} ", dbUrl );
		
		log.warn("datasource username is {}", username);
		log.warn("datasource password is {}", password);
		
		return factory.build();

	}

}
