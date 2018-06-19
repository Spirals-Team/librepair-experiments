package com.utn.tp5.service;

import com.utn.tp5.model.Airport;
import com.utn.tp5.model.Price;
import com.utn.tp5.model.Route;
import com.utn.tp5.model.RouteXCabin;
import com.utn.tp5.persistence.PricePersistence;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import com.utn.tp5.persistence.RouteXCabinPersistence;

@Service
@Transactional
@AllArgsConstructor
public class RouteXCabinService {

    @Autowired
    private RouteXCabinPersistence routeXCabinPersistence;

    public List<RouteXCabin> getAll() {
        return routeXCabinPersistence.findAll();
    }

    public RouteXCabin getById(Long id) {
        return routeXCabinPersistence.getOne(id);
    }

    public boolean saveRouteXCabin(RouteXCabin rxc) {
        RouteXCabin saved = routeXCabinPersistence.save(rxc);
        boolean rtn = false;
        if (saved.equals(rxc)) {
            rtn = true;
        }
        return rtn;
    }

    public List<RouteXCabin> getByRoute (Route route){
        return routeXCabinPersistence.getByRoute(route);
    }

    public boolean modifyRouteXCabin(RouteXCabin routeXCabin, Price newPrice) {
       /* routeXCabin.getPrice().add(newPrice);
        return this.saveRouteXCabin(routeXCabin);*/
       return true;
    }
}
