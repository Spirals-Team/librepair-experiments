package guru.bonacci.oogway.doorway.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.doorway.cheaters.Postponer;
import guru.bonacci.oogway.doorway.clients.AuthClient;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@ExtendWith(SpringExtension.class)
public class FirstLineSupportServiceTests {

	@TestConfiguration
	@Profile("!integration-test")
    static class TestContext {
  
        @Bean
        public FirstLineSupportService service() {
    		return new FirstLineSupportService();
        }
    }

	@Autowired
	FirstLineSupportService service;

	@MockBean
	AuthClient authClient;

	@MockBean
	OracleClient oracleClient;

	@MockBean
	Postponer postponer;

	@Test
	public void shouldGiveEmptyStringAnswer() {
		assertThat(service.enquire("", ""), is(equalTo(new GemCarrier("No question no answer..", "oogway"))));
	}

	@Test
	public void shouldGiveAnswer() {
		GemCarrier expected = new GemCarrier("some answer", "some person");
		when(authClient.user(anyString())).thenReturn(new Credentials());
		// Mockito.<String>any() allows for null
		when(oracleClient.consult(anyString(), Mockito.<String>any(), any(Credentials.class))).thenReturn(Optional.of(expected));

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
