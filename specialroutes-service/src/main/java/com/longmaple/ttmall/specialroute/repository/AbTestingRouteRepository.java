package com.longmaple.ttmall.specialroute.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.longmaple.ttmall.specialroute.model.AbTestingRoute;

@Repository
public interface AbTestingRouteRepository extends CrudRepository<AbTestingRoute, String>  {
    public AbTestingRoute findByServiceName(String serviceName);
}
