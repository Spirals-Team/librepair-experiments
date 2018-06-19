package com.utn.tp5.service;

import com.utn.tp5.model.Airport;
import com.utn.tp5.persistence.RoutePersistence;
import com.utn.tp5.model.Route;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RouteService {

    @Autowired
    private RoutePersistence routePersistence;

    public List<Route> getAll() {
        return routePersistence.findAll();
    }

    public Route getById(Long id) {
        return routePersistence.getOne(id);
    }

    public List<Route> getByOrigin(Airport airport){
        return routePersistence.getByOrigin(airport);
    }

    public Route getByOriginAndDestination(Airport origin, Airport destination){
        return routePersistence.getByOriginAndDestination(origin, destination);
    }

    public boolean saveRoute(Route r) {
        Route saved = routePersistence.save(r);
        boolean rtn = false;
        if (saved.equals(r)) {
            rtn = true;
        }
        return rtn;
    }

    public boolean deleteRoute(Long id){
        Route r = this.getById(id);
        return routePersistence.deleteByOriginAndDestination(r.getOrigin(), r.getDestination());
    }
}
