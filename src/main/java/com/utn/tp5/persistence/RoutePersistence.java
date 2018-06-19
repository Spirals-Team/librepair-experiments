package com.utn.tp5.persistence;

import com.utn.tp5.model.Airport;
import com.utn.tp5.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutePersistence extends JpaRepository<Route, Long> {
    List<Route> getByOrigin(Airport origin);
    Route getByOriginAndDestination(Airport origin, Airport destination);
    boolean deleteByOriginAndDestination(Airport origin, Airport destination);
}
