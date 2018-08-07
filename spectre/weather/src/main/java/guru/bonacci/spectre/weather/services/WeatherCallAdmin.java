package guru.bonacci.spectre.weather.services;

import org.springframework.stereotype.Component;

import guru.bonacci.spectre.spectreshared.collections.CustomPassiveExpiringCollection;
import guru.bonacci.spectre.spectreshared.collections.TooEnthusiasticWebserviceUsageException;

@Component
public class WeatherCallAdmin {

	// 60000 milliseconds in a minute
	private final CustomPassiveExpiringCollection<String> hitsAdministrator = new CustomPassiveExpiringCollection<>(60000l);

	public void checkWhetherCallIsAllowed(String id) throws TooEnthusiasticWebserviceUsageException {
		hitsAdministrator.add(id);

		// Maximum free usage of openweathermap is 60 calls per minute
		if (hitsAdministrator.size() > 60) 
			throw new TooEnthusiasticWebserviceUsageException("we're getting too popular...");
	}
}
