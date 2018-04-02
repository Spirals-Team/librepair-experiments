package guru.bonacci.oogway.web.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.web.WebTestApp;
import guru.bonacci.oogway.web.cheaters.Postponer;
import guru.bonacci.oogway.web.clients.OracleClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestApp.class, webEnvironment = NONE)
public class FirstLineSupportServiceTests {

	@Autowired
	FirstLineSupportService service;

	@MockBean
	OracleClient oracleClient;

	@MockBean
	Postponer postponer;

	@MockBean
	HttpServletRequest request;
	
	@Test
	public void shouldGiveEmptyStringAnswer() {
		assertThat(service.enquire(""), is(equalTo(new GemCarrier("No question no answer..", "oogway"))));
	}

	@Test
	public void shouldGiveAnswer() {
		GemCarrier expected = new GemCarrier("some answer", "some person");
		when(oracleClient.consult(anyString(), anyString())).thenReturn(Optional.of(expected));

		assertThat(service.enquire("some input"), is(equalTo(expected)));
	}

	@Test
	public void shouldGivePostponingAnswer() {
		String postponingAnswer = "wait a second..";
		when(oracleClient.consult(anyString(), anyString())).thenReturn(Optional.empty());
		when(postponer.saySomething()).thenReturn(postponingAnswer);

		assertThat(service.enquire("some input").getSaying(), is(equalTo(postponingAnswer)));
	}
}
