package com.d4dl.hellofib.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * This is the configuration class for <a target="_blank" href="https://swagger.io/">Swagger</a> auto-generated
 * REST endpoint documentation.  The implementation is provided by
 * <a target="_blank" href="http://springfox.github.io/springfox/">SpringFox</a>
 */
@EnableSwagger2
@Configuration
@Import(SpringDataRestConfiguration.class)
public class APIConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Default constructor
     */
    public APIConfiguration() {
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * A bean to configure SpringFox's swagger implementation.
     * An example can be found here:
     * <a target="_blank"
     *    href="http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api">Baeldung Swagger Config</a>
     * @return The Docket bean configured to expose all endpoints other than the internal endpoints.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/api/profile")))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Hello World and Fibonacci REST API",
                "Simple API demonstrating basic REST functionality and a REST based fibonacci generator.  " +
                "The functionality below allows you try various entity endpoints with sample data.",
                "1",
                "http://termsofservice.html",
                new Contact("Joshua DeFord", "https://github.com/JoshuaEDeFord", "jdeford@gmail.com"),
                "MIT License",
                "https://github.com/JoshuaEDeFord/HelloFib/blob/master/LICENSE",
                new ArrayList()
        );

        return apiInfo;
    }
}
