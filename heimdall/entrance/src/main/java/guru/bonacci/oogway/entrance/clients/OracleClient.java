package guru.bonacci.oogway.entrance.clients;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import guru.bonacci.oogway.entrance.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
public class OracleClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PasswordGrantFactoryConfig restTemplateFactory;

	private final String serviceUrl;

	public OracleClient(@Value("${oracle.service.url}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}

	/**
	 * A few small things are needed to secure a service..
	 * 
	 * The command line can pretend to be entrance-service:
	 * curl entrance-service:entrance-service-secret@localhost:5000/auth/oauth/token -d "grant_type=password&username=user1&password=password"
	 * curl -H "Authorization: Bearer 6d5e6804-d5fd-4cfc-a4e6-12138ed8f05b" -v http://localhost:4444/oracle/gems?q=dance
	 */
	@HystrixCommand(fallbackMethod = "fallback")
	public Optional<GemCarrier> consult(String searchString, String author, Credentials credentials) {
		String params = "?q={searchString}";
		if (author != null)
			params += "&by={author}";

		RestTemplate restTemplate = restTemplateFactory.restTemplate(credentials);
		return ofNullable(restTemplate.getForObject(serviceUrl + "/oracle/gems" + params, GemCarrier.class, searchString, author));
	}

	public Optional<GemCarrier> fallback(String searchString, String by, Credentials credentials, Throwable cause) {
        logger.error("Help!!! Can't reach the oracle...", cause);    
        return Optional.empty();
	}
}

