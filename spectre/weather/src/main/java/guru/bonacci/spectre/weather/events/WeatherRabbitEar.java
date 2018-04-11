package guru.bonacci.spectre.weather.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.spectre.weather.services.WeatherService;
import guru.bonacci.spectre.spectreshared.events.SpectreRabbitEar;

@Component
public class WeatherRabbitEar extends SpectreRabbitEar {

	@Autowired
	public WeatherRabbitEar(WeatherService service) {
		super(service);
	}
}
