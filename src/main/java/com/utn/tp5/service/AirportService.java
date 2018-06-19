package com.utn.tp5.service;


import com.utn.tp5.persistence.AirportPersistence;
import com.utn.tp5.model.Airport;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AirportService {

    @Autowired
    private AirportPersistence airportPersistence;

    public List<Airport> getAll() {
        return airportPersistence.findAll();
    }

    public Airport getById(Long id) {
        return airportPersistence.getOne(id);
    }

    public Airport getByIata(String name) {
        return airportPersistence.getByIata(name);
    }

    public Boolean saveAirport(Airport a) {
        Airport saved = airportPersistence.save(a);
        boolean rtn = false;
        if (saved.equals(a)) {
            rtn = true;
        }
        return rtn;
    }

    public boolean deleteAirport(String name) {
        return airportPersistence.deleteByName(name);
    }
    public boolean updateAirport(Airport airport, long id) {
        airport.setId(id);
        return this.saveAirport(airport);
    }

}
