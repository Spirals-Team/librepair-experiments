package com.pexper.springcloudplayground.feignclient.controller;

import com.pexper.springcloudplayground.feignclient.repository.RepositoryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @Autowired
    private RepositoryServiceClient repositoryServiceClient;

    @GetMapping("/eureka-client-repository-url/{eurekaClientName}")
    public @ResponseBody String getServiceURLFromEurikaClient(@PathVariable String eurekaClientName) {
        return repositoryServiceClient.getServiceURL(eurekaClientName);
    }

}
