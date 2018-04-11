package guru.bonacci.spectre.sentiment;


import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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

import guru.bonacci.spectre.sentiment.services.SentimentSpec;
import guru.bonacci.spectre.sentiment.services.SentimentSpecRepository;
import guru.bonacci.spectre.spectreshared.events.SpectreEventChannels;
import guru.bonacci.spectre.spectreshared.persistence.PersistenceTestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SentimentIntegrationTests {

	@Autowired
	BinderAwareChannelResolver resolver;

	String uuid;

	@Autowired 
	SentimentSpecRepository repo;

	@Before
	public void init() {
		uuid = UUID.randomUUID().toString();
		
		SentimentSpec spec = new SentimentSpec();
		spec.id = uuid;
		spec.message = "what a wonderful wonderful life";
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
		String body = "{\"content\":\"" + uuid + "\"}";
		sendMessage(body, SpectreEventChannels.ENRICHMENT,"application/json");

		SentimentSpec persisted = repo.findById(uuid).get();
		assertThat(persisted.message, is(equalTo("what a wonderful wonderful life")));
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
											value = {SentimentServer.class}))
	@EnableBinding(SpectreEventChannels.class)
	@IntegrationComponentScan
	@EnableElasticsearchRepositories
	@Import(PersistenceTestConfig.class)
	static class SentimentIntegrationTestApp {

		public static void main(String[] args) {
			SpringApplication.run(SentimentIntegrationTestApp.class, args);
		}
	}
}