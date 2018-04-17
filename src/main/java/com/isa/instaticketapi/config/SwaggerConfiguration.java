package com.isa.instaticketapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final ApplicationProperties applicationProperties;

    public SwaggerConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public Docket producApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.isa.instaticketapi.web.rest"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        ApiInfo apiInfo = new ApiInfo(
                applicationProperties.getSwagger().getTitle(),
                applicationProperties.getSwagger().getDescription(),
                applicationProperties.getSwagger().getVersion(),
                applicationProperties.getSwagger().getTermsOfServiceUrl(),
                applicationProperties.getSwagger().getContactName(),
                applicationProperties.getSwagger().getLicense(),
                applicationProperties.getSwagger().getLicenseUrl());
        return apiInfo;
    }

}
