package com.les.brouilles.planner.service.mapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfiguration {

	@Bean
	@Qualifier("mapperJsondevis")
	public ObjectMapper mapperJsondevis() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		// Fail if unknown!
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new Jdk8Module());
		return mapper;
	}

	@Bean
	public ObjectMapper objectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new Jdk8Module());
		return mapper;
	}
}
