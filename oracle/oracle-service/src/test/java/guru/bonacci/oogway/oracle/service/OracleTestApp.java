package guru.bonacci.oogway.oracle.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableAspectJAutoProxy 
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = {OracleServer.class}))
public class OracleTestApp {

	public static void main(String[] args) {
		SpringApplication.run(OracleTestApp.class, args);
	}
}