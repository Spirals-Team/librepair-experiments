package com.utn.tp5.persistence;

import com.utn.tp5.model.Price;
import com.utn.tp5.model.RouteXCabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PricePersistence extends JpaRepository<Price, Long> {
    List<Price> getByRouteXCabin(RouteXCabin routeXCabin);
}
