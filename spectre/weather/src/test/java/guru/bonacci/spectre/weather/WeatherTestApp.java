package guru.bonacci.spectre.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { WeatherServer.class }))
public class WeatherTestApp {

	public static void main(String[] args) {
		SpringApplication.run(WeatherTestApp.class, args);
	}
}