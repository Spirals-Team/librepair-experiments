package net.posesor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class in SpringBoot based application.
 */
@SpringBootApplication
public class Application {
	/**
	 * Entry point: starts the whole application.
	 * @param ignored command line arguments. Currently we don't use it, maybe later.
	 */
	public static void main(String[] ignored) {
		SpringApplication.run(Application.class, ignored);
	}
}
