package com.utn.tp5.controllers;

import com.utn.tp5.DTO.CityDTO;
import com.utn.tp5.model.City;
import com.utn.tp5.model.Country;
import com.utn.tp5.service.CityService;
import com.utn.tp5.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;

    @GetMapping(value = "/", produces = "application/json")
    public List<CityDTO> listCities() {
        List<City> cities = cityService.getAll();
        List<CityDTO> rtn = new ArrayList<>();
        for (City city : cities) {
            rtn.add(new CityDTO(city));
        }
        return rtn;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public CityDTO getCityById(@PathVariable("id") Long id) {
        City city = cityService.getById(id);
        CityDTO rtn = new CityDTO(city);
        return rtn;
    }

    @PostMapping(value = "/create")
    public void createCity(String name, String iata, Long id_country) {
        Country country = countryService.getById(id_country);
        City c = new City(name, iata, country);
        cityService.saveCity(c);
    }

    @DeleteMapping(value = "/delete/{name}")
    public void deleteCity(@PathVariable("name") String name) {
        cityService.deleteCity(name);
    }
}
