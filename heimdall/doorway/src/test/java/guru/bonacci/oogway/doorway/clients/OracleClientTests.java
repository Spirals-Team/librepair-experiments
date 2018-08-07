package guru.bonacci.oogway.doorway.clients;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.doorway.DoorwayServer;
import guru.bonacci.oogway.doorway.clients.OracleClientTests.App;
import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.doorway.security.TestDecryptor;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = App.class, webEnvironment = NONE, properties = {
	"hystrix.command.default.execution.timeout.enabled=false"
})
public class OracleClientTests {

    MockRestServiceServer server;

    @Qualifier("testTemplate")
    @Autowired
    RestTemplate rest;

	@MockBean
	AuthClient authClient;

	@MockBean
	Credentials credentials;

    @Autowired
    OracleClient client;
    
    @MockBean
    PasswordGrantFactoryConfig restTemplateFactory;
    
    @BeforeEach
    public void setup() {
        this.server = MockRestServiceServer.createServer(rest);
        when(restTemplateFactory.restTemplate(null)).thenReturn(rest);
    }

    @AfterEach
    public void teardown() {
        this.server = null;
    }

    @Test
	public void shouldAnswerQ() {
        this.server.expect(requestTo("http://not-used/oracle/gems?q=something"))
			        .andExpect(method(HttpMethod.GET))
			        .andRespond(withSuccess("{\"saying\":\"bla\"}", MediaType.APPLICATION_JSON));

		GemCarrier gem = client.consult("something", null, null).get();
		assertThat(gem).isEqualTo(new GemCarrier("bla"));
    }
    
    @Test
    public void shouldAnswerQAndBy() {
        this.server.expect(requestTo("http://not-used/oracle/gems?q=something&by=someone"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"saying\":\"bla\", \"author\":\"bloe\"}", MediaType.APPLICATION_JSON));
        
        GemCarrier gem = client.consult("something", "someone", null).get();
        assertThat(gem).isEqualTo(new GemCarrier("bla", "bloe"));
    }

    @Test
    public void shouldFallback() {
    	this.server.expect(requestTo("http://not-used/oracle/gems?q=something&by=someone"))
        		.andExpect(method(HttpMethod.GET))
        		.andRespond(withServerError());
        
        Optional<GemCarrier> gem = client.consult("something", "someone", null);
        assertThat(gem.isPresent()).isFalse();
    }

	@SpringBootApplication
	@EnableCircuitBreaker
	@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
							value = { DoorwayServer.class }))
	static class App {

		@Bean
		TestDecryptor decryptor() {
			return new TestDecryptor(); 
		}

		@Bean("testTemplate")
		RestTemplate restTemplate() {
			return new RestTemplate();
		}

		static void main(String[] args) {
	        SpringApplication.run(App.class, args);
	    }
	}
}