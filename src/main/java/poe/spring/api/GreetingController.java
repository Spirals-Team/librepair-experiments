package poe.spring.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poe.spring.domain.Greeting;

@RestController
public class GreetingController {

	@RequestMapping("/hello")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		System.out.println("/hello here");
		return new Greeting(1, "hello " + name);
	}

	@RequestMapping("/hi/name/{name}/lastname/{lastname}")
	public Greeting hi(@PathVariable(value = "name") String name, @PathVariable(value = "lastname") String lastname) {
		return new Greeting(1, "hi " + name);
	}
	
	
}