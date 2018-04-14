package com.prussia.play.spring.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
//@Profile("dev")
public class SwaggerConfig {
	/*
	 * The json data
	 * http://localhost:8080/v2/api-docs
	 * UI access
	 * http://localhost:8080/swagger-ui.html
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.prussia.test.play.spring.web.controller"))
				.paths(PathSelectors.ant("/api/*")).build().apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("playSpring REST API", "For personal practice", "API TOS",
				"Terms of service", new Contact("prussia.hu@gmail.com", "", ""), "License of API", "API license URL");

		return apiInfo;
	}

}