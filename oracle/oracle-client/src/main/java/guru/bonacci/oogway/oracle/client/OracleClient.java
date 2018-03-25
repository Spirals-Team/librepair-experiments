package guru.bonacci.oogway.oracle.client;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import feign.hystrix.FallbackFactory;
import guru.bonacci.oogway.oracle.client.OracleClient.HystrixClientFallbackFactory;
import guru.bonacci.oogway.shareddomain.GemCarrier;

/**
 * Talks to the Oracle via REST
 */
@FeignClient(name = "oracle-service", fallbackFactory = HystrixClientFallbackFactory.class)
public interface OracleClient {

	@RequestMapping(value = "/oracle/gems", method = GET)
	Optional<GemCarrier> consult(@RequestParam("q") String q, @RequestParam(value = "by") String author);

	@RequestMapping(value = "/oracle/gems/random", method = GET)
    Optional<GemCarrier> random();

	@Component
	static class HystrixClientFallbackFactory implements FallbackFactory<OracleClient> {

		private final Logger logger = getLogger(this.getClass());
		
		@Override
		public OracleClient create(Throwable cause) {
			return new OracleClient() {

				@Override
				public Optional<GemCarrier> consult(String q, String author) {
					sos();    
					return Optional.empty();
				}

				@Override
				public Optional<GemCarrier> random() {
					sos();    
					return Optional.empty();
				}
				
				private void sos() {
			        logger.error("Help!!! Can't reach the oracle...", cause);    
				}

			};
		}
	}
}

