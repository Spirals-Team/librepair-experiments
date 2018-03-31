package guru.bonacci.oogway.web.intercept;

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

import guru.bonacci.oogway.oracle.client.OracleClientCredentialsClient;
import guru.bonacci.oogway.web.WebTestApp;
import guru.bonacci.oogway.web.events.SpectreGateway;
import guru.bonacci.oogway.web.services.FirstLineSupportService;
import guru.bonacci.oogway.web.utils.IPCatcher;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestApp.class, webEnvironment = NONE)
public class BigBrotherTests {

	@Autowired
	FirstLineSupportService service;

	@Autowired
	BigBrother bigBrother;

	@MockBean
	OracleClientCredentialsClient oracleClient;

	@MockBean
	IPCatcher iPCatcher;

	@MockBean
	SpectreGateway gateway;

	@Test
	public void shouldInterceptTheConsultMethod() {
		String searchString = "something completely different";
		when(oracleClient.consult(anyString(), anyString())).thenReturn(Optional.empty());

		service.enquire(searchString);

		when(iPCatcher.getClientIp()).thenReturn("123");
		//TODO verify(gateway, times(1)).send(isA(COMINT.class));
	}
}
