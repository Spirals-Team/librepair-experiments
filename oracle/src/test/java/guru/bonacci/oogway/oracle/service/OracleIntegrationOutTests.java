package guru.bonacci.oogway.oracle.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Ignore;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import guru.bonacci.oogway.oracle.service.events.OracleEventChannels;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GenericEvent;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class OracleIntegrationOutTests {

	@Autowired
	MockMvc mvc;

	@Autowired
    OracleEventChannels channels;
	
	@Autowired
	MessageCollector messageCollector;

	@MockBean
	GemRepository repo; //thank you ES for your radical upgrade

	@SuppressWarnings("unchecked")
	@Test
	@Ignore // until further notice
	public void shouldSendMessageAfterInterception() throws Exception {
		String q = "The art of living is more like wrestling than dancing.";
		mvc.perform(get("/gems?q=" + q));
		
		Message<GenericEvent> received = (Message<GenericEvent>) messageCollector.forChannel(channels.oracleChannel()).poll();
		assertThat(received.getPayload(), equalTo(new GenericEvent(q)));
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