package com.meteoradesigner;

import com.meteoradesigner.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * This @code{MeteoraDesignerApplication} class launches Spring Boot Application.
 */
@SpringBootApplication
@Import(AppConfig.class)
public class MeteoraDesignerApplication {
    /**
     * Runs Spring Boot Application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(MeteoraDesignerApplication.class, args);
    }
}
