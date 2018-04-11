package guru.bonacci.oogway.entrance.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.entrance.EntranceTestApp;
import guru.bonacci.oogway.entrance.cheaters.Postponer;
import guru.bonacci.oogway.entrance.clients.AuthClient;
import guru.bonacci.oogway.entrance.clients.LumberjackClient;
import guru.bonacci.oogway.entrance.clients.OracleClient;
import guru.bonacci.oogway.entrance.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EntranceTestApp.class, webEnvironment = NONE)
public class FirstLineSupportServiceTests {

	@Autowired
	FirstLineSupportService service;

	@MockBean
	AuthClient authClient;

	@MockBean
	OracleClient oracleClient;

	@MockBean
	LumberjackClient lumberjackClient;

	@MockBean
	Postponer postponer;

	@MockBean
	HttpServletRequest request;
	
	@Test
	public void shouldGiveEmptyStringAnswer() {
		assertThat(service.enquire("", ""), is(equalTo(new GemCarrier("No question no answer..", "oogway"))));
	}

	@Test
	public void shouldGiveAnswer() {
		GemCarrier expected = new GemCarrier("some answer", "some person");
		when(authClient.user(anyString())).thenReturn(new Credentials());
		when(oracleClient.consult(anyString(), anyString(), any(Credentials.class))).thenReturn(Optional.of(expected));

		assertThat(service.enquire("some input", ""), is(equalTo(expected)));
	}

	@Test
	public void shouldGivePostponingAnswer() {
		String postponingAnswer = "wait a second..";
		when(authClient.user(anyString())).thenReturn(new Credentials());
		when(oracleClient.consult(anyString(), anyString(), any(Credentials.class))).thenReturn(Optional.empty());
		when(postponer.saySomething()).thenReturn(postponingAnswer);

		assertThat(service.enquire("some input", "").getSaying(), is(equalTo(postponingAnswer)));
	}
}
