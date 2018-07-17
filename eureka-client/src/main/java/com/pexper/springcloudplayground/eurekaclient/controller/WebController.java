package com.pexper.springcloudplayground.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/service-uri/{appName}")
    public @ResponseBody String getServiceURL(@PathVariable  String appName) {
        List<ServiceInstance> clientList = discoveryClient.getInstances(appName);
        if (clientList != null && !clientList.isEmpty()) {
            return clientList.get(0).getUri().toString();
        }
        return "service not registered";
    }

    @GetMapping("/service-instances/{appName}")
    public @ResponseBody List<ServiceInstance> getServiceInstances(@PathVariable  String appName) {
        return discoveryClient.getInstances(appName);
    }

}