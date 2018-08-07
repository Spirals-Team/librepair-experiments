package guru.bonacci.oogway.sannyas.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = { @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = {SannyasServer.class}),
								  @Filter(type = FilterType.ANNOTATION, 
								  		classes = TestConfiguration.class)})
public class SannyasTestApp {

	public static void main(String[] args) {
		SpringApplication.run(SannyasTestApp.class, args);
	}
}