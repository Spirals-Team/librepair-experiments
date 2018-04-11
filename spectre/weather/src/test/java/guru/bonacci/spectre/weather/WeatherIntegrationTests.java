package guru.bonacci.spectre.weather;


import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
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
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.spectre.spectreshared.events.SpectreEventChannels;
import guru.bonacci.spectre.spectreshared.persistence.PersistenceTestConfig;
import guru.bonacci.spectre.weather.services.WeatherSpec;
import guru.bonacci.spectre.weather.services.WeatherSpecRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
	"openweathermap.apikey=1234567890"		
})
public class WeatherIntegrationTests {

	@Autowired
	BinderAwareChannelResolver resolver;

	@Autowired 
	WeatherSpecRepository repo;

	@MockBean
	RestTemplate rest;

	String uuid;

	@Before
	public void init() {
		uuid = UUID.randomUUID().toString();
		
		WeatherSpec spec = new WeatherSpec();
		spec.id = uuid;
		spec.geoip.latitude=1.1;
		spec.geoip.longitude=2.2;
		repo.save(spec);
	}

	@After
	public void clean() {
		try {
			repo.deleteById(uuid);
		} catch (Exception ignore) {}
	}

	@Test
	public void shouldEventuallyAddData() {
		Map<String,Object> enrichmentData = new HashMap<>();
		enrichmentData.put("a", "is not b");
		doReturn(enrichmentData).when(rest).getForObject("http://api.openweathermap.org/data/2.5/weather?lat=1.1&lon=2.2&appid=1234567890", Map.class);
		
		String body = "{\"content\":\"" + uuid + "\"}";
		sendMessage(body, SpectreEventChannels.ENRICHMENT, "application/json");

		WeatherSpec persisted = repo.findById(uuid).get();
		assertThat(persisted.weather.get("a"), is(equalTo("is not b")));
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
											value = {WeatherServer.class}))
	@EnableBinding(SpectreEventChannels.class)
	@IntegrationComponentScan
	@EnableElasticsearchRepositories
	@Import(PersistenceTestConfig.class)
	static class WeatherIntegrationTestApp {

		public static void main(String[] args) {
			SpringApplication.run(WeatherIntegrationTestApp.class, args);
		}
	}
}