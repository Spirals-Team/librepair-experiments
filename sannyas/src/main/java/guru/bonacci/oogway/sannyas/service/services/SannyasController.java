package guru.bonacci.oogway.sannyas.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.sannyas.service.processing.PitchforkManager;

@RestController
public class SannyasController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

	@PostMapping("/backdoor")
	public void index(@RequestBody String input) {
		logger.info("Receiving secret request to process: '" + input + "'");

		manager.delegate(input);
	}

	@GetMapping("/version")
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
