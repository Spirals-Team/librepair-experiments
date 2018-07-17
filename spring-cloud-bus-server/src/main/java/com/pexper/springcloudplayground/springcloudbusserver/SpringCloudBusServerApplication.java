package com.pexper.springcloudplayground.springcloudbusserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SpringCloudBusServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudBusServerApplication.class, args);
	}
}
