package guru.bonacci.spectre.localtimer.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.spectre.localtimer.services.LocalTimerService;
import guru.bonacci.spectre.spectreshared.events.SpectreRabbitEar;

@Component
public class LocalTimerRabbitEar extends SpectreRabbitEar {

	@Autowired
	public LocalTimerRabbitEar(LocalTimerService service) {
		super(service);
	}
}
