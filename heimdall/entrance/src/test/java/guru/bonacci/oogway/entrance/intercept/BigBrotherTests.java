package guru.bonacci.oogway.entrance.intercept;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.entrance.EntranceTestApp;
import guru.bonacci.oogway.entrance.bigbrother.BigBrother;
import guru.bonacci.oogway.entrance.clients.AuthClient;
import guru.bonacci.oogway.entrance.clients.LumberjackClient;
import guru.bonacci.oogway.entrance.clients.OracleClient;
import guru.bonacci.oogway.entrance.events.SpectreGateway;
import guru.bonacci.oogway.entrance.security.Credentials;
import guru.bonacci.oogway.entrance.services.FirstLineSupportService;
import guru.bonacci.oogway.entrance.utils.IPCatcher;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EntranceTestApp.class, webEnvironment = NONE)
public class BigBrotherTests {

	@Autowired
	FirstLineSupportService service;

	@Autowired
	BigBrother bigBrother;

	@MockBean
	AuthClient authClient;
	
	@MockBean
	OracleClient oracleClient;

	@MockBean
	LumberjackClient lumberjackClient;

	@MockBean
	IPCatcher iPCatcher;

	@MockBean
	SpectreGateway gateway;

	@Test
	public void shouldInterceptTheConsultMethod() {
		String searchString = "something completely different";
		when(oracleClient.consult(anyString(), anyString(), any(Credentials.class))).thenReturn(Optional.empty());

		service.enquire(searchString, "some key");

		when(iPCatcher.getClientIp()).thenReturn("123");
		//TODO verify(gateway, times(1)).send(isA(COMINT.class));
	}
}
