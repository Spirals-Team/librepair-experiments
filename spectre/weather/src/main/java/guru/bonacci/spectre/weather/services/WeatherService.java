package guru.bonacci.spectre.weather.services;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.spectre.spectreshared.enrichment.SpectreService;
import guru.bonacci.spectre.spectreshared.persistence.Spec;
import guru.bonacci.spectre.spectreshared.persistence.SpecRepository;

@RefreshScope
@Service
public class WeatherService implements SpectreService {

	private final Logger logger = getLogger(this.getClass());

	public static final String serviceURL = "http://api.openweathermap.org/data/2.5/weather";
	public static final String searchQuery = "?lat=#lat#&lon=#lon#&appid=#apiKey#";

	// Help Java 9!!
	private static final Set<String> redundantFields = of("coord", "base", "sys", "id", "name", "cod", "dt").collect(toSet());

	@Autowired
	private WeatherCallAdmin weatherCallAdmin;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SpecRepository repo;

	@Value("${openweathermap.apikey}")
	private String apiKey;
	
	public void enrich(String id) {
		try {
			weatherCallAdmin.checkWhetherCallIsAllowed(id);	
			
			// Too lazy for refined error handling today...
			Spec spec = repo.findById(id).get();
			String q = searchQuery.replace("#lat#", String.valueOf(spec.geoip.latitude))
								  .replace("#lon#", String.valueOf(spec.geoip.longitude))
								  .replace("#apiKey#", apiKey);

			@SuppressWarnings("unchecked")
			Map<String,Object> enrichmentData = restTemplate.getForObject(serviceURL + q, Map.class);
			enrichmentData.keySet().removeAll(redundantFields);
			logger.debug(enrichmentData.toString());
			
			repo.addData("weather", enrichmentData, spec);
		} catch(Exception e) {
			logger.error("Oops", e);
		}
	}
}
