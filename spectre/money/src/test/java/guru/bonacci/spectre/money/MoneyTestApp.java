package guru.bonacci.spectre.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { MoneyServer.class }))
public class MoneyTestApp {

	public static void main(String[] args) {
		SpringApplication.run(MoneyTestApp.class, args);
	}
}