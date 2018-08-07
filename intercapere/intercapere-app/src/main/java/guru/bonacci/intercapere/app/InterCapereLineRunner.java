package guru.bonacci.intercapere.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.bonacci.intercapere.InterCapereService;

@Component
public class InterCapereLineRunner implements CommandLineRunner {

	private final InterCapereService service;

	public InterCapereLineRunner(InterCapereService service) {
		this.service = service;
	}

	@Override
	public void run(String... args) throws Exception {
		this.service.take("World");
	}
}
