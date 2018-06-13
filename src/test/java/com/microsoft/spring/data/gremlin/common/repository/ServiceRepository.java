/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.spring.data.gremlin.common.repository;

import com.microsoft.spring.data.gremlin.common.domain.Service;
import com.microsoft.spring.data.gremlin.common.domain.ServiceType;
import com.microsoft.spring.data.gremlin.repository.GremlinRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ServiceRepository extends GremlinRepository<Service, String> {

    List<Service> findByName(String name);

    List<Service> findByInstanceCount(int instanceCount);

    List<Service> findByIsActive(boolean isActive);

    List<Service> findByCreateAt(Date createAt);

    List<Service> findByProperties(Map<String, Object> properties);

    List<Service> findByNameAndInstanceCount(String name, int instanceCount);

    List<Service> findByNameOrInstanceCount(String name, int instanceCount);

    List<Service> findByNameAndInstanceCountAndType(String name, int instanceCount, ServiceType type);

    List<Service> findByNameAndIsActiveOrProperties(String name, boolean isActive, Map<String, Object> properties);

    List<Service> findByNameOrInstanceCountAndType(String name, int instanceCount, ServiceType type);

    List<Service> findByNameAndInstanceCountOrType(String name, int instanceCount, ServiceType type);

    List<Service> findByCreateAtAfter(Date expiryDate);

    List<Service> findByNameOrTypeAndInstanceCountAndCreateAtAfter(String name, ServiceType type, int instanceCount,
                                                                   Date expiryDate);

    List<Service> findByCreateAtBefore(Date expiryDate);

    List<Service> findByCreateAtAfterAndCreateAtBefore(Date startDate, Date endDate);
}
