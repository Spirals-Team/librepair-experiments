package com.longmaple.ttmall.messageservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longmaple.ttmall.messageservice.data.License;


@FeignClient("licensingservice")
public interface OrganizationFeignClient {
    @RequestMapping(
            method= RequestMethod.GET,
            value="v1/organizations/{organizationId}/licenses/",
            consumes="application/json")
    List<License> getLicenses(@PathVariable("organizationId") String organizationId);
}