package JoaoVFG.com.github.service.test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.config.MapConfig;
import JoaoVFG.com.github.repositories.config.MapConfigRepository;
import JoaoVFG.com.github.service.utils.GenerateRandom;

@Service
public class DBServiceInicializeKeys {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MapConfigRepository mapConfigRepository;
	
	public void inicializeKeys() {

		GenerateRandom gr = new GenerateRandom();
		
		MapConfig mapConfig = mapConfigRepository.findBynameKey("JWTSECRET");
		mapConfig.setValue(passwordEncoder.encode(gr.newRandom(20)));
		
		MapConfig mapConfig2 = mapConfigRepository.findBynameKey("CRYPTOSECRET");
		mapConfig2.setValue(passwordEncoder.encode(gr.newRandom(15)));
		
		
		mapConfigRepository.saveAll(Arrays.asList(mapConfig,mapConfig2));
		
		
	}
}
