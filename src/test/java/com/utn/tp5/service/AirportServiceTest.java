package com.utn.tp5.service;


import com.utn.tp5.persistence.AirportPersistence;
import com.utn.tp5.model.Airport;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AirportServiceTest {

    private AirportService airportService;
    private Airport airport;
    private AirportPersistence airportPersistence;

    @Before
    public void contextLoads() {
        this.airportPersistence = mock(AirportPersistence.class);
        this.airportService = new AirportService(airportPersistence);
        this.airport = mock(Airport.class);
        when(airportPersistence.save(this.airport)).thenReturn(this.airport);
    }

    @Test
    public void whenAnAirportIsSaved() {
        Boolean res = this.airportService.saveAirport(this.airport);
        assertEquals(Boolean.TRUE, res);
    }

    @Test
    public void whenAnAirportIsDeleted() {
        when(airportPersistence.save(this.airport)).thenReturn(this.airport);
        when(airportPersistence.deleteByName(this.airport.getName())).thenReturn(true);
        this.airportService.saveAirport(this.airport);
        Boolean res = this.airportService.deleteAirport(this.airport.getName());
        assertEquals(Boolean.TRUE, res);
    }

    @Test
    public void whenAnAirportIsAskedByIata() {
        when(airportPersistence.getByIata(this.airport.getIata())).thenReturn(this.airport);
        Airport c = this.airportService.getByIata(this.airport.getIata());
        assertEquals(this.airport, c);
    }

    @Test
    public void whenTheAirportListIsAsked() {
        List<Airport> expected = new ArrayList<>();
        when(airportPersistence.findAll()).thenReturn(expected);
        List<Airport> cities = this.airportService.getAll();
        assertEquals(cities, expected);
    }

    @Test
    public void whenAnAirportIsModified() {
        this.airport.setName("Example");
        this.airportService.updateAirport(this.airport, this.airport.getId());
        verify(this.airportPersistence).save(this.airport);
    }
}
