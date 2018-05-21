package com.hipermidia.campeonato.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class DocumentacaoSwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2 )

                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hipermidia.campeonato.controller") )
                .paths(PathSelectors.any() )
                .build()
                .apiInfo( this.informacoesDaAPI() );
    }

    private ApiInfo informacoesDaAPI() {
        return new ApiInfo(
                "API REST - Sistema de Gerenciamento de Campeonato de Futebol",
                "API desenvolvida com Spring Boot, Spring Web e Spring REST.",
                "1.0",
                "Termo de Serviço",
                new Contact("Wenner Sanner", "www.wennersanner.com", "wennersanner@hotmail.com"),
                "MIT", "API license URL", Collections.emptyList() );

    }
}
