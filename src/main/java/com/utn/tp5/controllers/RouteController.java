package com.utn.tp5.controllers;

import com.utn.tp5.DTO.RouteDTO;

import com.utn.tp5.service.AirportService;
import com.utn.tp5.service.RouteService;
import com.utn.tp5.model.Airport;
import com.utn.tp5.model.Route;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;
    @Autowired
    private AirportService airportService;

    @GetMapping(value = "/", produces = "application/json")
    public List<RouteDTO> listRoutes() {
        List<Route> routes = routeService.getAll();
        List<RouteDTO> rtn = new ArrayList<>();
        for (Route route: routes) {
            rtn.add(new RouteDTO(route));
        }
        return rtn;
    }

    @GetMapping(value = "/{iata}", produces = "application/json")
    public List<RouteDTO> getRouteByOrigin(@PathVariable("iata") String iata) {
        Airport airport = airportService.getByIata(iata);
        List<RouteDTO> rtn = new ArrayList<>();
        List<Route> routes = routeService.getByOrigin(airport);
        for (Route route: routes) {
            rtn.add(new RouteDTO(route));
        }
        return rtn;
    }

    @PostMapping(value = "/create")
    public boolean createRoute(float distance, Long id_origin, Long id_destination) {
        Airport origin = airportService.getById(id_origin);
        Airport destination = airportService.getById(id_destination);
        Route route = new Route(distance, origin, destination);
        return routeService.saveRoute(route);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteRoute(@PathVariable("name") Long id){
        return routeService.deleteRoute(id);
    }
}
