package guru.bonacci.oogway.jobs.clients;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import feign.hystrix.FallbackFactory;
import guru.bonacci.oogway.jobs.clients.OracleClient.HystrixClientFallbackFactory;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RefreshScope
@FeignClient( name = "${application.name.oracle}", 
			  fallbackFactory = HystrixClientFallbackFactory.class)
public interface OracleClient {

	@GetMapping("/oracle/gems/random")
    Optional<GemCarrier> random();

	@Component
	static class HystrixClientFallbackFactory implements FallbackFactory<OracleClient> {

		private final Logger logger = getLogger(this.getClass());
		
		@Override
		public OracleClient create(Throwable cause) {
			return new OracleClient() {

				@Override
				public Optional<GemCarrier> random() {
			        logger.error("Help!!! Can't reach the oracle...", cause);    
					return Optional.empty();
				}
			};
		}
	}
}

