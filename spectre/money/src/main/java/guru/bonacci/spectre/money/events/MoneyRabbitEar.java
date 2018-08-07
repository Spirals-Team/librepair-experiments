package guru.bonacci.spectre.money.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.spectre.money.services.MoneyService;
import guru.bonacci.spectre.spectreshared.events.SpectreRabbitEar;

@Component
public class MoneyRabbitEar extends SpectreRabbitEar {

	@Autowired
	public MoneyRabbitEar(MoneyService service) {
		super(service);
	}
}
