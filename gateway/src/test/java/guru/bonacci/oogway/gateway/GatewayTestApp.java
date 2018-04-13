package guru.bonacci.oogway.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

import guru.bonacci.oogway.gateway.GatewayApp;

import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { GatewayApp.class }))
public class GatewayTestApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayTestApp.class, args);
    }
}