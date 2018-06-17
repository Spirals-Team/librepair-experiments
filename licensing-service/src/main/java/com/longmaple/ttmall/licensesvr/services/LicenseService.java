package com.longmaple.ttmall.licensesvr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longmaple.ttmall.licensesvr.config.ServiceConfig;
import com.longmaple.ttmall.licensesvr.model.License;
import com.longmaple.ttmall.licensesvr.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	ServiceConfig config;

	public License getLicense(String organizationId,String licenseId) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		return license.withComment(config.getExampleProperty());
	}

	@HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
			threadPoolKey = "licenseByOrgThreadPool",
			threadPoolProperties =
		{@HystrixProperty(name = "coreSize",value="30"),
		 @HystrixProperty(name="maxQueueSize", value="10")},
		commandProperties = {
				@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
				@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
				@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
				@HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
				@HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
			)
	public List<License> getLicensesByOrg(String organizationId){
		return licenseRepository.findByOrganizationId( organizationId );
	}
	
	@SuppressWarnings("unused")
	private List<License> buildFallbackLicenseList(String organizationId){
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId( organizationId )
                .withProductName("Sorry no licensing information currently available");

        fallbackList.add(license);
        return fallbackList;
}

	public void saveLicense(License license){
		license.withId( UUID.randomUUID().toString());

		licenseRepository.save(license);

	}

	public void updateLicense(License license){
		licenseRepository.save(license);
	}

	public void deleteLicense(License license){
		licenseRepository.delete(license);
	}

}
