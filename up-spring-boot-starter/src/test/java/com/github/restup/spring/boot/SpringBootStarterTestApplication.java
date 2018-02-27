package com.github.restup.spring.boot;

import javax.persistence.EntityManager;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = {"com.university"})
public class SpringBootStarterTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterTestApplication.class, args);
    }

    @Bean
    public EntityManager entityManager() {
        // XXX it seems spring boot is not loading everything correctly when
        // starters are added as test dependencies???
        // this is a hack just to test the Up! starters
        return Mockito.mock(EntityManager.class);
    }

}
