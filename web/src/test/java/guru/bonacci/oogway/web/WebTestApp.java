package guru.bonacci.oogway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.oracle.client.OracleClientTestConfig;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { WebServer.class }))
@Import(OracleClientTestConfig.class)
public class WebTestApp {

	public static void main(String[] args) {
		SpringApplication.run(WebTestApp.class, args);
	}
}