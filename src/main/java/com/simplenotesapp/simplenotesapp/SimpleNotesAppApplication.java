package com.simplenotesapp.simplenotesapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SimpleNotesAppApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleNotesAppApplication.class);

    public static void main(String[] args) {
        log.info("Starting application");
        SpringApplication.run(SimpleNotesAppApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SimpleNotesAppApplication.class);
    }
}
