package guru.bonacci.oogway.entrance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import guru.bonacci.oogway.entrance.security.TestDecryptor;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { EntranceServer.class }))
@EnableFeignClients
public class EntranceTestApp {

	@Bean
	public TestDecryptor decryptor() {
		return new TestDecryptor(); 
	}

	public static void main(String[] args) {
		SpringApplication.run(EntranceTestApp.class, args);
	}
}