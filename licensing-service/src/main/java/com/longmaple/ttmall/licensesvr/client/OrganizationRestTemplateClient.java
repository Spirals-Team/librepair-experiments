package com.longmaple.ttmall.licensesvr.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.longmaple.ttmall.licensesvr.model.Organization;

@Component
public class OrganizationRestTemplateClient {
	
	@Autowired
	RestTemplate restTemplate;

	public Organization getOrganization(String organizationId){
		ResponseEntity<Organization> restExchange =
				restTemplate.exchange(
						"http://organizationservice/v1/organizations/{organizationId}",
						HttpMethod.GET,
						null, Organization.class, organizationId);

		return restExchange.getBody();
	}
}