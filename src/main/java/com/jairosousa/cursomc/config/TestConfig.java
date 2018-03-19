package com.jairosousa.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jairosousa.cursomc.services.DBService;
import com.jairosousa.cursomc.services.EmailService;
import com.jairosousa.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instanciateTestDataBase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
