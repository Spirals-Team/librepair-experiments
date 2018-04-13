package guru.bonacci.oogway.doorway.events;

import static guru.bonacci.oogway.doorway.events.DoorwayEventChannels.SPECTRE;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import guru.bonacci.oogway.shareddomain.COMINT;


@MessagingGateway
public interface SpectreGateway {
	
	@Gateway(requestChannel = SPECTRE)
	void send(COMINT comint);
}
