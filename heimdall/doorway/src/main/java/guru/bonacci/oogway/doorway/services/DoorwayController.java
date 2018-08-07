package guru.bonacci.oogway.doorway.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@RefreshScope
@RestController
public class DoorwayController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private FirstLineSupportService service;

	@GetMapping("/consult")
	public GemCarrier enquire(@RequestParam("q") String q, @RequestParam("apikey") String apiKey) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");

		return service.enquire(q, apiKey);
	}
	
	@GetMapping("/version")
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	


	@Value("${demo.message:Demo effect, no config read..}")
    private String message;

    @GetMapping("/demo")
    public String getMessage() {
        return this.message;
    }
}
