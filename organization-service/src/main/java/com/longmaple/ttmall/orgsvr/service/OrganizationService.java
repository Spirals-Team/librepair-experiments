package com.longmaple.ttmall.orgsvr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longmaple.ttmall.orgsvr.data.OrganizationRepo;
import com.longmaple.ttmall.orgsvr.model.Organization;

import java.util.UUID;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepo orgRepository;

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId).get();
    }

    public void saveOrg(Organization org){
        org.setId( UUID.randomUUID().toString());

        orgRepository.save(org);

    }

    public void updateOrg(Organization org){
        orgRepository.save(org);
    }

    public void deleteOrg(Organization org){
        orgRepository.delete( org );
    }
}