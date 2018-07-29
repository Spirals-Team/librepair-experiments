package com.longmaple.ttmall.messageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.longmaple.ttmall.messageservice.client.UserFeignClientInterceptor;

import feign.RequestInterceptor;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableResourceServer
public class MessageServiceApplication {
	
	@Bean
    public RequestInterceptor getUserFeignClientInterceptor() {
        return new UserFeignClientInterceptor();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}
}