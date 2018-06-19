package com.utn.tp5.controllers;

import com.utn.tp5.DTO.AirportDTO;
import com.utn.tp5.service.AirportService;
import com.utn.tp5.service.CityService;
import com.utn.tp5.model.Airport;
import com.utn.tp5.model.City;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    private AirportService airportService;
    @Autowired
    private CityService cityService;
    @GetMapping(value = "/", produces = "application/json")
    public List<AirportDTO> listAirports() {
       List<Airport> airports = airportService.getAll();
       List<AirportDTO> rtn = new ArrayList<>();
       for (Airport airport : airports) {
           rtn.add(new AirportDTO(airport));
       }
       return rtn;
    }

    @GetMapping(value = "/{iata}", produces = "application/json")
    public AirportDTO getAirportByIata(@PathVariable("iata") String iata) {
        Airport airport = airportService.getByIata(iata);
        AirportDTO rtn = new AirportDTO(airport);
        return rtn;
    }

    @PostMapping(value = "/create")
    public void createAirport(String name, String iata, Long id_city, float latitude, float longitude) {
        City city = cityService.getById(id_city);
        Airport a = new Airport(name, iata, city, latitude, longitude);
        airportService.saveAirport(a);
    }

    @PutMapping(value = "/modify")
    public void modifyById(@RequestBody Airport airport, Long id) {
        airportService.updateAirport(airport, id);
    }

    @DeleteMapping(value = "/delete/{name}")
    public void deleteAirport(@PathVariable("name") String name) {
        airportService.deleteAirport(name);
    }

}
