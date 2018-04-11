package guru.bonacci.oogway.entrance.clients;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guru.bonacci.oogway.entrance.security.Credentials;

@RefreshScope
@FeignClient( name = "${application.name.auth}", 
			  configuration = CredentialsConfig.class)
public interface AuthClient {

	@RequestMapping(value = "/auth/users", method = GET)
    Credentials user(@RequestParam("apikey") String apiKey);
}