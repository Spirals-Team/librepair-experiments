package guru.bonacci.oogway.jobs.clients;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.Managed;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "feign.hystrix.enabled=true",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
@ContextConfiguration(classes = {OracleClientTests.LocalRibbonClientConfiguration.class})
@ActiveProfiles("unit-test")
@ExtendWith(WireMockExtension.class)
public class OracleClientTests {

	@Managed 
	WireMockServer s1 = with(wireMockConfig().port(123));

	@Autowired
    OracleClient client;

	@Autowired
	ObjectMapper objectMapper;

    @Test
    public void shouldFindRandom() throws JsonProcessingException {
    	GemCarrier gem = new GemCarrier("bla", "bloe");
        stubFor(get(urlEqualTo("/oracle/gems/random"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(gem))));
        
		assertEquals(client.random().get(), gem);
    }

    @Test
    public void shouldFallbackRandom() {
        stubFor(get(urlEqualTo("/oracle/gems/random"))
                .willReturn(aResponse()
                        .withStatus(500)));
        
        Optional<GemCarrier> gem = client.random();
        assertThat(gem.isPresent(), is(false));
    }

	@SpringBootApplication
	@EnableFeignClients(basePackageClasses = OracleClient.class)
	@EnableCircuitBreaker
	static class App {
	    static void main(String[] args) {
	        SpringApplication.run(App.class, args);
	    }
	}

    @TestConfiguration
    static class LocalRibbonClientConfiguration {
        @Bean
        ServerList<Server> ribbonServerList() {
            return new StaticServerList<>(new Server("localhost",  123));
        }
    }
}