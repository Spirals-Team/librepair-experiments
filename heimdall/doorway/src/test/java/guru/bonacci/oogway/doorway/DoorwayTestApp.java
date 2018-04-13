package guru.bonacci.oogway.doorway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import guru.bonacci.oogway.doorway.DoorwayServer;
import guru.bonacci.oogway.doorway.security.TestDecryptor;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { DoorwayServer.class }))
@EnableFeignClients
public class DoorwayTestApp {

	@Bean
	public TestDecryptor decryptor() {
		return new TestDecryptor(); 
	}

	public static void main(String[] args) {
		SpringApplication.run(DoorwayTestApp.class, args);
	}
}