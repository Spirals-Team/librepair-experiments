package guru.bonacci.spectre.money.services;

import static java.util.stream.Collectors.toConcurrentMap;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MoneyCache {

	private final Logger logger = getLogger(this.getClass());

	public static final String serviceURL = "http://api.worldbank.org/countries?format=json";

	private final Map<String,Object> cache = new HashMap<>();
	
	@Autowired
	private RestTemplate restTemplate;

	public String get(String key) {
		return (String)cache.get(key);
	}

	@PostConstruct
	private void retrieveBanqueMondialeData() {
		try {
			for (int i=1; i<=howManyPages(); i++)
				cache.putAll(retrievePage(i));
		} catch(Exception e) {
			logger.error("Oops", e);
		}
	}
	
	/**
	 * When all else fails there's always delusion.
	 * @param pagenr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int howManyPages() {
		//[0] is general data
		Object[] allData = restTemplate.getForObject(serviceURL, Object[].class);
		Object pages = ((Map)allData[0]).get("pages"); 
		return (int)pages;
	}

	/**
	 * When all else fails we can whip the horse's eyes and make them sleep ...
	 * @param pagenr
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String,Object> retrievePage(int pagenr) {
		//[1] is the actual data as an array with Maps
		Object[] allData = restTemplate.getForObject(serviceURL + "&page=" + pagenr, Object[].class);
		List<Map> theData = (List<Map>)allData[1]; 
		return theData.stream()
					  .collect(toConcurrentMap(map -> (String) map.get("iso2Code"),
							  			       map -> ((Map) map.get("incomeLevel")).get("value")));
	}
}
