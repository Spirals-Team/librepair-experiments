package guru.bonacci.oogway.sannyas.service.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.sannyas.service.processing.PitchforkManager;

@RestController
public class SannyasController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

	@RequestMapping(path = "/backdoor", method = POST)
	public void index(@RequestBody String input) {
		logger.info("Receiving secret request to process: '" + input + "'");

		manager.delegate(input);
	}

	@RequestMapping(path = "/version", method = GET)
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
