package com.pexper.springcloudplayground.ribbonclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class WebController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/eureka-client-service-url/{eurekaClientName}")
    public @ResponseBody String getServiceURLFromEurikaClient(@PathVariable String eurekaClientName) {

        // See eureka-client WebController.java
        // http:// + <spring.application.name> + /service-uri/ + <spring.application.name>
        // ribbon will find some instance of eureka-client registered in eureka-server
        return restTemplate.getForObject("http://" + eurekaClientName + "/service-uri/" + eurekaClientName, String.class);
    }

}
