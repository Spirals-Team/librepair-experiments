package guru.bonacci.oogway.entrance;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import guru.bonacci.oogway.entrance.clients.AuthClient;
import guru.bonacci.oogway.entrance.clients.LumberjackClient;
import guru.bonacci.oogway.entrance.clients.OracleClient;
import guru.bonacci.oogway.entrance.events.EntranceEventChannels;
import guru.bonacci.oogway.entrance.security.Credentials;
import guru.bonacci.oogway.entrance.security.TestDecryptor;
import guru.bonacci.oogway.shareddomain.COMINT;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "security.basic.enabled=false"
}, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class EntranceIntegrationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
    EntranceEventChannels channels;
	
	@Autowired
	MessageCollector messageCollector;

	@MockBean
	AuthClient authClient;

	@MockBean
	OracleClient oracleClient;

	@MockBean
	LumberjackClient lumberjackClient;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendMessageAfterInterception() throws Exception {
		when(authClient.user(anyString())).thenReturn(new Credentials());
		when(oracleClient.consult(anyString(), anyString(), any(Credentials.class))).thenReturn(Optional.empty());

		String localIP = "127.0.0.1";
		String input = "The art of living is more like wrestling than dancing.";
		mvc.perform(get("/consult?q=" + input + "&apikey=somekey"));
		
		Message<COMINT> received = (Message<COMINT>) messageCollector.forChannel(channels.spectreChannel()).poll();
		assertThat(received.getPayload(), equalTo(new COMINT(localIP, input)));
	}
	
	@SpringBootApplication
	@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
											value = {EntranceServer.class}))
	@EnableBinding(EntranceEventChannels.class)
	@IntegrationComponentScan
	static class EntranceIntegrationTestApp {

		@Bean
		public TestDecryptor decryptor() {
			return new TestDecryptor(); 
		}

		static void main(String[] args) {
			SpringApplication.run(EntranceIntegrationTestApp.class, args);
		}
	}
}