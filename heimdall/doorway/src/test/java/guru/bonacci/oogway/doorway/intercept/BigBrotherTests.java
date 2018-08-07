package guru.bonacci.oogway.doorway.intercept;

import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.intercapere.InterCapereService;
import guru.bonacci.oogway.doorway.DoorwayTestApp;
import guru.bonacci.oogway.doorway.clients.AuthClient;
import guru.bonacci.oogway.doorway.clients.LumberjackClient;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.events.SpectreGateway;
import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.doorway.services.FirstLineSupportService;
import guru.bonacci.oogway.doorway.utils.IPCatcher;
import guru.bonacci.oogway.shareddomain.COMINT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DoorwayTestApp.class, properties = {
        "spring.sleuth.enabled=false",
        "spring.zipkin.enabled=false"
}, webEnvironment = NONE)
public class BigBrotherTests {

	@Autowired
	FirstLineSupportService service;

	@MockBean
	AuthClient authClient;
	
	@MockBean
	OracleClient oracleClient;

	@MockBean
	InterCapereService intercapere;

	@MockBean
	LumberjackClient lumberjackClient;

	@MockBean
	IPCatcher iPCatcher;

	@MockBean
	SpectreGateway gateway;

	@Test
	public void shouldInterceptMethod() {
		String searchString = "something completely different";
		when(oracleClient.consult(anyString(), anyString(), any(Credentials.class))).thenReturn(empty());

		service.enquire(searchString, "some key");

		verify(lumberjackClient, times(1)).visits("some key");

		when(iPCatcher.getClientIp()).thenReturn("123");
		verify(gateway, times(1)).send(isA(COMINT.class));
	}
}
