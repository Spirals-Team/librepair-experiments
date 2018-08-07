package guru.bonacci.oogway.doorway.clients;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RefreshScope
@FeignClient( name = "${application.name.lumberjack}")
public interface LumberjackClient {

	@GetMapping(value = "/lumber/visits/{apikey}")
    Long visits(@PathVariable("apikey") String apiKey);
}