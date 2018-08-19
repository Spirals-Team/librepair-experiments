package JoaoVFG.com.github.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.config.MapConfig;
import JoaoVFG.com.github.repositories.config.MapConfigRepository;

@Service
public class MapConfigService {

	@Autowired
	MapConfigRepository configRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	public List<MapConfig> findAll(){
		return configRepository.findAll();
	}
	
	public MapConfig insereGoogleApiKey(String googleApiKey) {
		MapConfig mapConfig = configRepository.findBynameKey("GOOGLEAPIKEY");
		mapConfig.setValue(encoder.encode(googleApiKey));
		return configRepository.save(mapConfig);
	}
	
	public MapConfig findGoogleApiKey() {
		MapConfig mapConfig = configRepository.findBynameKey("GOOGLEAPIKEY");
		return mapConfig;
	}
	
	
}
