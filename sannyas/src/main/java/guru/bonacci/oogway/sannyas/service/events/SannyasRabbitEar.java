package guru.bonacci.oogway.sannyas.service.events;

import static guru.bonacci.oogway.sannyas.service.events.SannyasEventChannels.ORACLE;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.service.processing.PitchforkManager;
import guru.bonacci.oogway.shareddomain.GenericEvent;

@Component
public class SannyasRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

    @StreamListener(ORACLE)
	public void onMessage(GenericEvent event) {
		logger.error("An opportunity to learn... '" + event.getContent() + "'");//error: to show load balancing
		manager.delegate(event.getContent());
	}
}
