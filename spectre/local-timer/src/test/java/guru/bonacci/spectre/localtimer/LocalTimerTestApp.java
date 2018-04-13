package guru.bonacci.spectre.localtimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

import guru.bonacci.spectre.localtimer.LocalTimerServer;

import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { LocalTimerServer.class }))
public class LocalTimerTestApp {

	public static void main(String[] args) {
		SpringApplication.run(LocalTimerTestApp.class, args);
	}
}