package com.les.brouilles.planner.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CorsConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {

				log.info("Ajout du CORS *");

				registry.addMapping("/**").allowedMethods("*").allowedHeaders("*").allowedOrigins("*");
			}
		};
	}
}
