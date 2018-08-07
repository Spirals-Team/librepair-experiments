package guru.bonacci.spectre.spectreshared.events;

import static guru.bonacci.spectre.spectreshared.events.SpectreEventChannels.ENRICHMENT;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;

public abstract class SpectreRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	private final SpectreService service;
	
	public SpectreRabbitEar(SpectreService service) {
		this.service = service;
	}

	@StreamListener(ENRICHMENT)
	public void onMessage(GenericEvent event) {
		logger.info("Incoming... " + event.getContent());

		service.enrich(event.getContent());
	}	
}
