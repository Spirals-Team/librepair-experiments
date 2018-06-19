package com.utn.tp5.service;

import com.utn.tp5.persistence.CityPersistence;
import com.utn.tp5.model.City;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CityServiceTest {

    private CityService cityService;
    private City city;
    private CityPersistence cityPersistence;

    @Before
    public void contextLoads() {
        this.cityPersistence = mock(CityPersistence.class);
        this.cityService = new CityService(cityPersistence);
        this.city = mock(City.class);
    }

    @Test
    public void whenACityIsSaved() {
        when(cityPersistence.save(this.city)).thenReturn(this.city);
        Boolean res = this.cityService.saveCity(this.city);
        assertEquals(Boolean.TRUE, res);
    }

    @Test
    public void whenACityIsDeleted() {
        when(cityPersistence.save(this.city)).thenReturn(this.city);
        when(cityPersistence.deleteByName(this.city.getName())).thenReturn(true);
        this.cityService.saveCity(this.city);
        Boolean res = this.cityService.deleteCity(this.city.getName());
        assertEquals(Boolean.TRUE, res);
    }

    @Test
    public void whenACityIsAskedById() {
        when(cityPersistence.getOne(this.city.getId())).thenReturn(this.city);
        City c = this.cityService.getById(this.city.getId());
        assertEquals(this.city, c);
    }

    @Test
    public void whenTheCityListIsAsked() {
        List<City> expected = new ArrayList<>();
        when(cityPersistence.findAll()).thenReturn(expected);
        List<City> cities = this.cityService.getAll();
        assertEquals(cities, expected);
    }
}
