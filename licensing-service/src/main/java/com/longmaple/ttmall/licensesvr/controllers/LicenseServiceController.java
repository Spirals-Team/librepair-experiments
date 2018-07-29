package com.longmaple.ttmall.licensesvr.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.bind.annotation.RestController;

import com.longmaple.ttmall.licensesvr.client.OrganizationClient;
import com.longmaple.ttmall.licensesvr.client.OrganizationFeignClient;
import com.longmaple.ttmall.licensesvr.model.EMallUser;
import com.longmaple.ttmall.licensesvr.model.License;
import com.longmaple.ttmall.licensesvr.model.Organization;
import com.longmaple.ttmall.licensesvr.services.LicenseService;
import com.longmaple.ttmall.licensesvr.utils.UserContext;
import com.longmaple.ttmall.licensesvr.utils.UserContextHolder;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);
	
    @Autowired
    private LicenseService licenseService;
    
    @Autowired
    private OrganizationClient organizationClient;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<License> getLicenses(OAuth2Authentication auth, @PathVariable("organizationId") String organizationId) {
    	UserContextHolder.getContext();
		logger.debug("LicenseServiceController Correlation id: {}", UserContext.getCorrelationId());
    	Organization org = organizationClient.getOrganization(organizationId);
    	logger.info("the name of organization = " + org.getContactName());
        return licenseService.getLicensesByOrg(organizationId);
    }

    @RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
    public License getLicenses( @PathVariable("organizationId") String organizationId,
                                @PathVariable("licenseId") String licenseId) {
        return licenseService.getLicense(organizationId,licenseId);
    }

    @RequestMapping(value="{licenseId}",method = RequestMethod.PUT)
    public String updateLicenses( @PathVariable("licenseId") String licenseId) {
        return String.format("This is the put");
    }

    @RequestMapping(value="/",method = RequestMethod.POST)
    public void saveLicenses(@RequestBody License license) {
       licenseService.saveLicense(license);
    }

    @RequestMapping(value="{licenseId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicenses( @PathVariable("licenseId") String licenseId) {
        return String.format("This is the Delete");
    }
}
