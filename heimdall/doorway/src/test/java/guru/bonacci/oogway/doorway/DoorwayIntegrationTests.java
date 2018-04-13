package guru.bonacci.oogway.doorway;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.bonacci.oogway.doorway.DoorwayServer;
import guru.bonacci.oogway.doorway.clients.AuthClient;
import guru.bonacci.oogway.doorway.clients.LumberjackClient;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.events.DoorwayEventChannels;
import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.doorway.security.TestDecryptor;
import guru.bonacci.oogway.shareddomain.COMINT;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.sleuth.enabled=false",
        "spring.zipkin.enabled=false"
}, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class DoorwayIntegrationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
    DoorwayEventChannels channels;
	
	@Autowired
	MessageCollector messageCollector;

	@MockBean
	AuthClient authClient;

	@MockBean
	OracleClient oracleClient;

	@MockBean
	LumberjackClient lumberjackClient;

	@Autowired
	ObjectMapper objectMapper;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendMessageAfterInterception() throws Exception {
		when(authClient.user(anyString())).thenReturn(new Credentials());
		when(oracleClient.consult(anyString(), anyString(), any(Credentials.class))).thenReturn(Optional.empty());

		String localIP = "127.0.0.1";
		String input = "The art of living is more like wrestling than dancing.";
		mvc.perform(get("/consult?q=" + input + "&apikey=somekey"));
		
		Message<COMINT> received = (Message<COMINT>) messageCollector.forChannel(channels.spectreChannel()).poll();
		String receivedAsString = objectMapper.writeValueAsString(received.getPayload()).replaceAll("\\\\", "");
		// terribly ugly, but you get the point...
		String somehowExpected = "\"" + objectMapper.writeValueAsString(new COMINT(localIP, input)) + "\"";
		assertThat(receivedAsString, equalTo(somehowExpected));
	}
	
	@SpringBootApplication
	@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
											value = {DoorwayServer.class}))
	@EnableBinding(DoorwayEventChannels.class)
	@IntegrationComponentScan
	static class DoorwayIntegrationTestApp {

		@Bean
		public TestDecryptor decryptor() {
			return new TestDecryptor(); 
		}

		static void main(String[] args) {
			SpringApplication.run(DoorwayIntegrationTestApp.class, args);
		}
	}
}