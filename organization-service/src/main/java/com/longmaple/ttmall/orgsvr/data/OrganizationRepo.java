package com.longmaple.ttmall.orgsvr.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.longmaple.ttmall.orgsvr.model.Organization;

public interface OrganizationRepo extends CrudRepository<Organization, String>  {
    public Optional<Organization> findById(String organizationId);
}