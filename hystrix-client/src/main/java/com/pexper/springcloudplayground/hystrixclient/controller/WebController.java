package com.pexper.springcloudplayground.hystrixclient.controller;

import com.pexper.springcloudplayground.hystrixclient.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/eureka-client-repository-url/{eurekaClientName}")
    public @ResponseBody String getServiceURLFromEurikaClient(@PathVariable String eurekaClientName) {
        return serviceRepository.getServiceURL(eurekaClientName);
    }

}
