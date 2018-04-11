package guru.bonacci.spectre.money;


import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

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

import guru.bonacci.spectre.money.services.MoneyCache;
import guru.bonacci.spectre.money.services.MoneySpec;
import guru.bonacci.spectre.money.services.MoneySpecRepository;
import guru.bonacci.spectre.spectreshared.events.SpectreEventChannels;
import guru.bonacci.spectre.spectreshared.persistence.PersistenceTestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MoneyIntegrationTests {

	@Autowired
	BinderAwareChannelResolver resolver;

	@Autowired 
	MoneySpecRepository repo;

	@MockBean
	MoneyCache cache;

	String uuid;

	@Before
	public void init() {
		uuid = UUID.randomUUID().toString();
		
		MoneySpec spec = new MoneySpec();
		spec.id = uuid;
		spec.geoip.country_code2="NZ";
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
		String enrichmentData = "aaaaa lot";
		doReturn(enrichmentData).when(cache).get("NZ");
		
		String body = "{\"content\":\"" + uuid + "\"}";
		sendMessage(body, SpectreEventChannels.ENRICHMENT, "application/json");

		MoneySpec persisted = repo.findById(uuid).get();
		assertThat(persisted.income, is(equalTo("aaaaa lot")));
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
											value = {MoneyServer.class}))
	@EnableBinding(SpectreEventChannels.class)
	@IntegrationComponentScan
	@EnableElasticsearchRepositories
	@Import(PersistenceTestConfig.class)
	static class MoneyIntegrationTestApp {

		public static void main(String[] args) {
			SpringApplication.run(MoneyIntegrationTestApp.class, args);
		}
	}
}