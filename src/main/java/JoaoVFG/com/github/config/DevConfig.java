package JoaoVFG.com.github.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import JoaoVFG.com.github.service.test.DBServiceInicializeKeys;
import JoaoVFG.com.github.service.test.DBServiceTest;
import JoaoVFG.com.github.service.test.DBServiceTestPopulateCEPS;
import JoaoVFG.com.github.service.test.DBServiceTestUsers;


@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBServiceTest dbServiceTeste;
	
	@Autowired
	private DBServiceTestPopulateCEPS populateCeps;
	
	@Autowired
	private DBServiceTestUsers users;
	
	@Autowired
	private DBServiceInicializeKeys keys;
	
	@Value("${joaovfg.github.generateDataToDatabse}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		keys.inicializeKeys();
		
		if(!"create".equals(strategy)) {
			return false;
		}
		
		dbServiceTeste.instantiateTesteDataBase();
		populateCeps.populateDBCeps();
		users.instantiabeDBUsers();
		return true;
	}

}
