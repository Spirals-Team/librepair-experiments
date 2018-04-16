package com.gdc.aerodev.web;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * This application works only if PostrgeSQL server started with configured 'application.yml' flyway properties
 */
@SpringBootApplication (scanBasePackages = "com.gdc.aerodev")
public class WebServiceApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebServiceApp.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}