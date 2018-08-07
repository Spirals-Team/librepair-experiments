package guru.bonacci.oogway.oracle.service;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static org.slf4j.LoggerFactory.getLogger;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import guru.bonacci.oogway.oracle.service.events.OracleEventChannels;
import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.oracle.service.security.CustomUserInfoTokenServices;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableEurekaClient
@EnableSwagger2
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@IntegrationComponentScan
@EnableBinding(OracleEventChannels.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class OracleServer extends ResourceServerConfigurerAdapter {

	private final Logger logger = getLogger(this.getClass());
	
	@Autowired
	private ResourceServerProperties sso;

	public static void main(String[] args) {
		SpringApplication.run(OracleServer.class, args);
	}

	@Bean
	CommandLineRunner demo(Environment env, GemRepository repo) {
		return args -> {
			// creative exclusion, is it not?
			if (env.acceptsProfiles("!unit-test")) {
				try {
					Gem[] friedrichsBest = readToList("nietzsche.txt").stream()
																		.map(quote -> new Gem(quote, "Friedrich Nietzsche"))
																		.toArray(Gem[]::new);
					repo.saveTheNewOnly(friedrichsBest);
				} catch (IOException e) {
					logger.error("Nietzsche!!", e);
				}
			}	

		};
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
    public Docket gemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(basePackage("guru.bonacci.oogway.oracle.service.services"))
                .paths(regex("/gems.*"))
                .build()
                .apiInfo(metaData());
    }
	
	private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Oracle Service REST API",
                "REST API for Oracle Service",
                "1.0",
                "Terms of service",
                new Contact("Leonardo Bonacci", "https://en.wikipedia.org/wiki/Fibonacci", "aabcehmu@mailfence.com"),
                "Eyn aruhath hinam :-)",
                "http://www.insidefortlauderdale.com/1500/Operating-without-a-License");
        return apiInfo;
    }
}
