package com.pexper.springcloudplayground.feignclient.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eureka-client")
public interface RepositoryServiceClient {

    @GetMapping("/service-uri/{appName}")
    String getServiceURL(@PathVariable("appName") String appName);

}


