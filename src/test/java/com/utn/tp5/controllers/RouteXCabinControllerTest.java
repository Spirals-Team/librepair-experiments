package com.utn.tp5.controllers;

import com.utn.tp5.model.*;
import com.utn.tp5.service.*;
import com.utn.tp5.DTO.RouteXCabinDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RouteXCabinControllerTest {

    private RouteXCabinController routeXCabinController;
    private RouteXCabinService routeXCabinService;
    private RouteService routeService;
    private CabinService cabinService;
    private PriceService priceService;
    private AirportService airportService;
    private RouteXCabin routeXCabin;
    private Cabin cabin;
    private Route route;
    private Price price;
    private Airport origin;
    private Airport destination;
    private List<RouteXCabin> routeXCabins;
    private List<RouteXCabinDTO> routeXCabinsDTO;
    private List<Price> pricesList;


    @Before
    public void contextLoads() {
        this.routeXCabinService = mock(RouteXCabinService.class);
        this.routeService = mock(RouteService.class);
        this.cabinService = mock(CabinService.class);
        this.priceService = mock(PriceService.class);
        this.airportService = mock(AirportService.class);

        this.routeXCabinController = new RouteXCabinController(routeXCabinService, routeService, cabinService, priceService, airportService);
        this.cabin = new Cabin("Example");
        this.origin = new Airport("Example", "Exa", new City("Example", "Exa", new Country("Example", "Exa")), 1010, 0101);
        this.destination = new Airport("Example2", "Exa2", new City("Example2", "Exa2", new Country("Example2", "Exa2")), 5050, 0505);
        this.route = new Route(1234, this.origin, this.destination);
        this.price = new Price(1234, new Date());
        this.pricesList = new ArrayList<>();
        this.pricesList.add(this.price);
        this.routeXCabin = new RouteXCabin(this.cabin, this.route);
        this.routeXCabin.setId((long) 1);
        this.routeXCabins = new ArrayList<>();
        this.routeXCabinsDTO = new ArrayList<>();
        routeXCabins.add(this.routeXCabin);

        when(this.routeXCabinService.getById((long) 1)).thenReturn(this.routeXCabin);
        when(this.routeXCabinService.saveRouteXCabin(this.routeXCabin)).thenReturn(true);
        when(this.airportService.getByIata(this.origin.getIata())).thenReturn(this.origin);
        when(this.airportService.getByIata(this.destination.getIata())).thenReturn(this.destination);
        when(this.routeService.getByOriginAndDestination(this.origin, this.destination)).thenReturn(this.route);
        when(this.routeXCabinService.getByRoute(this.route)).thenReturn(this.routeXCabins);

    }

    @Test
    public void whenRouteXCabinListIsAsked() {
        routeXCabinsDTO = routeXCabinController.listRoutesXCabins();
        for (RouteXCabin routeXCabin : routeXCabins) {
            routeXCabinsDTO.add(new RouteXCabinDTO(routeXCabin, pricesList));
        }
        assertEquals(routeXCabins.get(0).getCabin().getName(), routeXCabinsDTO.get(0).getCabin().getName());
    }

    @Test
    public void whenARouteXCabinIsAskedByOriginAndDestination() {
        routeXCabinsDTO = routeXCabinController.getRouteByOriginAndDestination(this.routeXCabin.getRoute().getOrigin().getIata(),this.routeXCabin.getRoute().getDestination().getIata());
        for (RouteXCabin routeXCabin: routeXCabins) {
            routeXCabinsDTO.add(new RouteXCabinDTO(routeXCabin, pricesList));
        }
        assertEquals(routeXCabins.get(0).getCabin().getName(), routeXCabinsDTO.get(0).getCabin().getName());
    }

    @Test
    public void whenARouteXCabinIsModified() throws ParseException {
        boolean res = this.routeXCabinController.modifyRouteXCabin(this.routeXCabin.getId(),1015,"10-10-2018");
        assertEquals(Boolean.TRUE, res);
    }
}
