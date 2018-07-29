package com.longmaple.ttmall.messageservice.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.ttmall.messageservice.client.OrganizationFeignClient;

@RestController
public class MessageController {
	
	@Autowired
	private OrganizationFeignClient feignClient;

	@RequestMapping("/")
	public Message home() {
		return new Message("Hello World - " + 
				feignClient.getLicenses("442adb6e-fa58-47f3-9ca2-ed1fecdfe86c").get(0).getProductName());
	}
}

class Message {
	private String id = UUID.randomUUID().toString();
	private String content;

	Message() {
	}

	public Message(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
