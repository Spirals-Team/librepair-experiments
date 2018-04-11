package guru.bonacci.oogway.oracle.service.events;

import static guru.bonacci.oogway.oracle.service.events.OracleEventChannels.SANNYAS;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.service.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
public class OracleRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

    @StreamListener(SANNYAS)
	public void onMessage(GemCarrier event) {
		logger.info("Receiving an extra bit of knowledge: '" + event + "'");
		repo.saveTheNewOnly(GemMapper.MAPPER.toGem(event));
	}
}
