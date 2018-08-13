package JoaoVFG.com.github.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import JoaoVFG.com.github.service.test.DBServiceTest;


@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBServiceTest dbServiceTeste;
	
	@Value("${joaovfg.github.generateDataToDatabse}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		
		if(!"create".equals(strategy)) {
			return false;
		}
		
		dbServiceTeste.instantiateTesteDataBase();
		
		return true;
	}

}
