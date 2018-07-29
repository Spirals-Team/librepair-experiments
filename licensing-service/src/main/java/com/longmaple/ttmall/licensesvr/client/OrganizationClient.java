package com.longmaple.ttmall.licensesvr.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.longmaple.ttmall.licensesvr.model.Organization;
import com.longmaple.ttmall.licensesvr.repository.OrganizationRedisRepository;
import com.longmaple.ttmall.licensesvr.utils.UserContext;

@Component
public class OrganizationClient {
	
    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRedisRepository orgRedisRepo;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationClient.class);

    private Organization checkRedisCache(String organizationId) {
        try {
            return orgRedisRepo.findOrganization(organizationId);
        }
        catch (Exception ex){
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", organizationId, ex);
            return null;
        }
    }

    private void cacheOrganizationObject(Organization org) {
        try {
            orgRedisRepo.saveOrganization(org);
        }catch (Exception ex){
            logger.error("Unable to cache organization {} in Redis. Exception {}", org.getId(), ex);
        }
    }

    public Organization getOrganization(String organizationId){
        logger.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());

        Organization org = checkRedisCache(organizationId);

        if (org!=null) {
            logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
            return org;
        }

        logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);

        org = organizationFeignClient.getOrganization(organizationId);

        if (org != null) {
            cacheOrganizationObject(org);
        }

        return org;
    }


}
