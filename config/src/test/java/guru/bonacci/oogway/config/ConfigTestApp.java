package guru.bonacci.oogway.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

import guru.bonacci.oogway.config.ConfigServer;

import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { ConfigServer.class }))
public class ConfigTestApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigTestApp.class, args);
    }
}