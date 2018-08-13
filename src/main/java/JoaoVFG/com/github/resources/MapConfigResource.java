package JoaoVFG.com.github.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.entity.config.MapConfig;
import JoaoVFG.com.github.service.MapConfigService;

@RestController
@RequestMapping(value = "/configs")
public class MapConfigResource {
	
	@Autowired
	MapConfigService mapConfigService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaConfigs", method = RequestMethod.GET)
	public ResponseEntity<List<MapConfig>> findAll(){
		List<MapConfig> mapsConfigs = mapConfigService.findAll();
		return ResponseEntity.ok(mapsConfigs);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/alteragoogleapi/{value}", method = RequestMethod.POST)
	public ResponseEntity<MapConfig> updateGoogleApiConfig(@PathVariable String value){
		MapConfig mapConfig =  mapConfigService.insereGoogleApiKey(value);
		return ResponseEntity.ok(mapConfig);
	}
}
