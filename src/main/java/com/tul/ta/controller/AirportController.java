package com.tul.ta.controller;

import com.tul.ta.mapper.AirportDtoMapper;
import com.tul.ta.model.airport.Airport;
import com.tul.ta.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class AirportController {

    @Autowired
    AirportService airportService;

    @Autowired
    AirportDtoMapper airportDtoMapper;

    @RequestMapping(value = "/airports", method = RequestMethod.GET)
    public ResponseEntity getAllAirports() {
        List<Airport> airportList = airportService.getAll();
        airportList.sort(Comparator.comparing(Airport::getCityName));
        return ResponseEntity.ok(airportList);
    }

    @RequestMapping(value = "/airports/{id}", method = RequestMethod.GET)
    public ResponseEntity getAirportById(@PathVariable(value = "id") String airportCode) {
        return ResponseEntity.ok(airportService.getAirportById(airportCode));
    }
}
