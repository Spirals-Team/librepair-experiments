package uo.asw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InciDashboardApplication {
	// Ahora no te dejará lanzarla porque no está mapeada la clase que se indica en el EjemploRepository
	public static void main(String[] args) {
		SpringApplication.run(InciDashboardApplication.class, args);
	}
}
