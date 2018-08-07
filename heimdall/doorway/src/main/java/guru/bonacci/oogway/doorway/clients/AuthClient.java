package guru.bonacci.oogway.doorway.clients;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guru.bonacci.oogway.doorway.security.Credentials;

@RefreshScope
@FeignClient( name = "${application.name.auth}", 
			  configuration = CredentialsConfig.class)
public interface AuthClient {

	@GetMapping(value = "/auth/users")
    Credentials user(@RequestParam("apikey") String apiKey);
}