package guru.bonacci.oogway.entrance;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import guru.bonacci.oogway.entrance.clients.CredentialsConfig;
import guru.bonacci.oogway.entrance.events.EntranceEventChannels;
import guru.bonacci.oogway.entrance.security.Decryptor;
import guru.bonacci.oogway.entrance.security.RSADecryptor;
import guru.bonacci.oogway.utils.security.RSAKeyHelper;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { CredentialsConfig.class })) 
@EnableFeignClients
@EnableEurekaClient
@EnableCircuitBreaker
@EnableBinding(EntranceEventChannels.class)
@IntegrationComponentScan
public class EntranceServer {

	@Bean
	public Decryptor decryptor() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		return new RSADecryptor(RSAKeyHelper.loadPrivateKey("/ubuntu1/")); //volume mount in Dockerfile
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EntranceServer.class, args);
	}
	
	@Configuration
	@EnableWebSecurity
	protected static class webSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
		    .authorizeRequests()
			    .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()//FIXME
			    .antMatchers("/**").permitAll()//FIXME
		    .and()
		        .httpBasic();
			// @formatter:on
		}
	}
}
