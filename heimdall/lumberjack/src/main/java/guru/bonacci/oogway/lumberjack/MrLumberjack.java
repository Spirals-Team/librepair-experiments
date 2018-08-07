package guru.bonacci.oogway.lumberjack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import guru.bonacci.oogway.lumberjack.persistence.Log;
import guru.bonacci.oogway.lumberjack.persistence.LogService;
import guru.bonacci.oogway.lumberjack.security.CustomUserInfoTokenServices;

@EnableMongoRepositories
@EnableEurekaClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class MrLumberjack extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private ResourceServerProperties sso;

	public static void main(String[] args) {
		SpringApplication.run(MrLumberjack.class, args);
	}

	@LoadBalanced
	@Bean
	public OAuth2RestTemplate loadBalancedTemplate() {
		BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
		return new OAuth2RestTemplate(resource);
	}
	
	@Bean
	public ResourceServerTokenServices tokenServices() {
		CustomUserInfoTokenServices serv = new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
		serv.setRestTemplate(loadBalancedTemplate());
		return serv;
	}

	@Bean
	public AbstractMongoEventListener<Log> mongoEventListener(LogService service) {
	    return service.new LogPersistListener();
	}
}
