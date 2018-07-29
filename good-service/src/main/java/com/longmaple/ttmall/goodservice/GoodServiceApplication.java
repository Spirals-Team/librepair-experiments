package com.longmaple.ttmall.goodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GoodServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodServiceApplication.class, args);
	}
}
