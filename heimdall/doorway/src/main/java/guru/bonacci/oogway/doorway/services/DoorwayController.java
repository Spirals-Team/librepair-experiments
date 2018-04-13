package guru.bonacci.oogway.doorway.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@RefreshScope
@Controller
public class DoorwayController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private FirstLineSupportService service;

	@ResponseBody
	@RequestMapping(path = "/consult", method = GET)
	public GemCarrier enquire(@RequestParam("q") String q, @RequestParam("apikey") String apiKey) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");

		return service.enquire(q, apiKey);
	}
	
	@ResponseBody
	@RequestMapping(path = "/version", method = GET)
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	


	@Value("${demo.message:Demo effect, no config read..}")
    private String message;

	@ResponseBody
    @RequestMapping("/demo")
    String getMessage() {
        return this.message;
    }
}
