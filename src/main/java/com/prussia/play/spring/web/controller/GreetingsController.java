package com.prussia.play.spring.web.controller;

import com.prussia.play.spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Thymeleaf samples
 * Add the following in pom.xml
 *  	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
 *
 */
@Controller
public class GreetingsController {
	
	
	@Autowired
	AccountService service;
	
	
	@RequestMapping("/api/greetings")
	public String greetingTp(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		
		//invoke the service
		service.createAcctount("accountNo", "username");
		
		return "greetingTp";
	}
}
