package guru.bonacci.oogway.sannyas.service.events;

import static guru.bonacci.oogway.sannyas.service.events.SannyasEventChannels.SANNYAS;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@MessagingGateway
public interface SannyasGateway {
	
	@Gateway(requestChannel = SANNYAS)
	void send(GemCarrier event);
}
