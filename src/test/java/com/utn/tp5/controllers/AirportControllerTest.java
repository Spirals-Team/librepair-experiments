package com.utn.tp5.controllers;

import com.utn.tp5.model.Country;
import com.utn.tp5.service.AirportService;
import com.utn.tp5.service.CityService;
import com.utn.tp5.DTO.AirportDTO;
import com.utn.tp5.model.Airport;
import com.utn.tp5.model.City;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AirportControllerTest {

    private AirportController airportController;
    private AirportService airportService;
    private CityService cityService;
    private Airport airport;
    private City city;

    @Before
    public void contextLoads() {
        this.airportService = mock(AirportService.class);
        this.cityService = mock(CityService.class);
        this.airportController = new AirportController(airportService, cityService);
        this.city = new City("Example", "Exa", new Country("Example", "Exa"));
        this.airport = new Airport("Example", "Exa", this.city, (float) 10.234, (float) 15.2324);
        this.airport.setId((long) 1);
        when(this.airportService.getByIata(this.airport.getIata())).thenReturn(this.airport);
        when(this.airportService.saveAirport(this.airport)).thenReturn(true);
    }

    @Test
    public void whenAirportListIsAsked() {
        List<Airport> airports = new ArrayList<>();
        airports.add(this.airport);
        when(this.airportService.getAll()).thenReturn(airports);
        List<AirportDTO> DTOList = airportController.listAirports();
        for (Airport airport : airports) {
            DTOList.add(new AirportDTO(airport));
        }
        assertEquals(airports.get(0).getName(), DTOList.get(0).getName());
    }

    @Test
    public void whenAnAirportIsAskedByIata() {
        AirportDTO a = airportController.getAirportByIata(this.airport.getIata());
        AirportDTO b = new AirportDTO(this.airport);
        assertEquals(a.getName(), b.getName());
    }

    @Test
    public void whenAnAirportIsAdded() {
        /*this.airportController.createAirport(this.airport);
        verify(this.airportService).saveAirport(this.airport);*/
    }

    @Test
    public void whenAnAirportIsModified() {
        this.airport.setName("Example two");
        this.airportController.modifyById(this.airport, this.airport.getId());
        verify(this.airportService).updateAirport(this.airport, this.airport.getId());
        }

    @Test
    public void whenAnAirportIsDeleted() {
        this.airportController.deleteAirport(this.airport.getName());
        verify(this.airportService).deleteAirport(this.airport.getName());
    }
}
