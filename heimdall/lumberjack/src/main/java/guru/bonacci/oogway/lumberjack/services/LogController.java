package guru.bonacci.oogway.lumberjack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.lumberjack.persistence.Log;
import guru.bonacci.oogway.lumberjack.persistence.LogService;

@RestController
public class LogController {

	@Autowired
	private LogService service;
	
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@GetMapping("/visits/{apikey}")
	public Long log(@PathVariable("apikey") String apiKey) {
		return service.insert(new Log(apiKey));
	}
}
