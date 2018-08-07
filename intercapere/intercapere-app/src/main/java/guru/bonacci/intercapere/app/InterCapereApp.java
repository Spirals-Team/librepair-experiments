package guru.bonacci.intercapere.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import guru.bonacci.intercapere.InterCapereService;

@SpringBootApplication
public class InterCapereApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(InterCapereApp.class).run(args);
	}
	
	@Bean
	public InterCapereService override() {
		return new OverrideService();
	}
}
