package com.d4dl.hellofib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A Spring Boot hello world application demonstrating some basic
 * <a target="_blank" href="https://spring.io/understanding/HATEOAS">HATEOS</a> REST functionality.
 * As well as a {@link com.d4dl.hellofib.fibonacci.FibonacciController} endpoint
 * a {@link com.d4dl.hellofib.greeting.GreetingRepository} REST endpoint
 * that wraps calls to <a target="_blank" href="https://jsonplaceholder.typicode.com">JSONPlaceholder Posts</a>
 * into HATEOS entities.
 */
@SpringBootApplication
public class Application {

    /**
     * Default constructor
     */
    public Application() {
    }

    /**
     * This along with the SpringBootApplication annotation setup the spring application and
     * the REST endpoints.
     * @param args required to serve as the application endpoint but not used in this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
