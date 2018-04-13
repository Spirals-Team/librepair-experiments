package guru.bonacci.oogway.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import guru.bonacci.oogway.gateway.filter.AccessLogFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApp.class, args);
	}

	@Bean
	public AccessLogFilter simpleFilter() {
		return new AccessLogFilter();
	}
}
