package guru.bonacci.oogway.oracle.service;

import static java.util.Collections.singletonMap;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.service.events.OracleEventChannels;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class OracleIntegrationInTests {

	@Autowired
	BinderAwareChannelResolver resolver;

	@MockBean
	GemRepository repo; //thank you ES for your radical upgrade
	
	@Test
	public void shouldDoSomething() throws Exception {
		String randomMessage = UUID.randomUUID().toString(); //non-existing
		
		String body = "{\"saying\":\"" + randomMessage + "\",\"author\":\"bla\"}";
		sendMessage(body, OracleEventChannels.SANNYAS,"application/json");

		// Through Spring-data's creative design for extensible
		// repositories the test-verification of the call 
		// gemRepository.save(newOnes) is not straightforward.
		// It is left as an exercise to the reader :)
	}

	private void sendMessage(String body, String target, Object contentType) {
		resolver.resolveDestination(target).send(MessageBuilder.createMessage(body,
				new MessageHeaders(singletonMap(CONTENT_TYPE, contentType))));
	}
	
	@Bean
	public MessageChannel routerChannel() {
		return new DirectChannel();
	}
	
	@SpringBootApplication
	@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
											value = {OracleServer.class}))
	@EnableBinding(OracleEventChannels.class)
	@IntegrationComponentScan
	static class OracleIntegrationTestApp {

		static void main(String[] args) {
			SpringApplication.run(OracleIntegrationTestApp.class, args);
		}
	}
}