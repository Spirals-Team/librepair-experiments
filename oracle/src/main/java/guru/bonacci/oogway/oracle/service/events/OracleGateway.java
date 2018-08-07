package guru.bonacci.oogway.oracle.service.events;

import static guru.bonacci.oogway.oracle.service.events.OracleEventChannels.ORACLE;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import guru.bonacci.oogway.shareddomain.GenericEvent;

@MessagingGateway
public interface OracleGateway {
	
	@Gateway(requestChannel = ORACLE)
	void send(GenericEvent event);
}
