package JoaoVFG.com.github.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import JoaoVFG.com.github.service.test.DBServiceTest;



@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBServiceTest dbServiceTeste;
	
	@Bean
	public boolean instantiateDatabase(){
		
		dbServiceTeste.instantiateTesteDataBase();
		
		return true;
	}
	
}
