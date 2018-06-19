package com.utn.tp5.service;

import com.utn.tp5.persistence.CountryPersistence;
import com.utn.tp5.model.Country;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryServiceTest {

    private CountryPersistence countryPersistence;
    private CountryService countryService;
    private Country country;

    @Before
    public void contextLoads() {
        countryPersistence = mock(CountryPersistence.class);
        this.countryService = new CountryService(countryPersistence);
        this.country = mock(Country.class);
    }

    @Test
    public void whenTheCountryListIsAsked() {
        List<Country> expected = new ArrayList<>();
        when(countryPersistence.findAll()).thenReturn(expected);
        List<Country> countries = this.countryService.getAll();
        assertEquals(countries, expected);
    }

    @Test
    public void whenACountryIsAskedById() {
        when(countryPersistence.getOne(this.country.getId())).thenReturn(this.country);
        Country c = this.countryService.getById(this.country.getId());
        assertEquals(this.country, c);
    }

    @Test
    public void whenACountryIsSaved() {
        when(countryPersistence.save(this.country)).thenReturn(this.country);
        Boolean res = this.countryService.saveCountry(this.country);
        assertEquals(Boolean.TRUE, res);
    }
    
    @Test
    public void whenACountryIsDeleted() {
        when(countryPersistence.save(this.country)).thenReturn(this.country);
        this.countryService.saveCountry(this.country);
        when(countryPersistence.deleteByName(this.country.getName())).thenReturn(true);
        Boolean res = this.countryService.deleteCountry(this.country.getName());
        assertEquals(Boolean.TRUE, res);
    }
}
