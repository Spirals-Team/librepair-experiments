package com.roldukhine.phonebook.web;

import com.roldukhine.services.configuration.ServicesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ComponentScan("com.roldukhine.phonebook.web")
@Import(ServicesConfiguration.class)
public class WebConfiguration {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(20971520L); // 20MB
        commonsMultipartResolver.setMaxInMemorySize(1048576); // 1MB
        return commonsMultipartResolver;
    }

}
