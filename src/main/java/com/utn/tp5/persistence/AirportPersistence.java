package com.utn.tp5.persistence;

import com.utn.tp5.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportPersistence extends JpaRepository<Airport, Long> {
    boolean deleteByName(String name);
    Airport getByIata(String iata);
}
