package com.tul.ta.service;

import com.tul.ta.exception.ResourceNotFoundException;
import com.tul.ta.model.airport.Airport;
import com.tul.ta.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DefaultAirportService implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport getAirportById(String airportCode) {
        return airportRepository.findById(airportCode)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", airportCode));
    }

    @Override
    public void save(Airport temp) {
        airportRepository.saveAndFlush(temp);
    }
}