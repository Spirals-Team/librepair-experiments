package com.utn.tp5.persistence;

import com.utn.tp5.model.Price;
import com.utn.tp5.model.Route;
import com.utn.tp5.model.RouteXCabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteXCabinPersistence extends JpaRepository<RouteXCabin, Long> {
    List<RouteXCabin> getByRoute(Route route);
}
