package apna.Maholla.controller;

import apna.Maholla.model.City;
import apna.Maholla.model.Country;
import apna.Maholla.model.State;
import apna.Maholla.repository.CityRepository;
import apna.Maholla.repository.CountryRepository;
import apna.Maholla.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @Autowired
    public AddressController(CityRepository cityRepository, CountryRepository countryRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }

    @GetMapping("/country")
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @GetMapping("/state")
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @GetMapping("/city")
    public List<City> getAllcities() {
        return cityRepository.findAll();
    }
}
