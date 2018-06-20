package com.tul.ta.controller;

import com.tul.ta.dto.schedule.FlightSchedulesDto;
import com.tul.ta.enums.AirportCode;
import com.tul.ta.mapper.FlightDtoMapper;
import com.tul.ta.model.schedule.Flight;
import com.tul.ta.model.trip.Trip;
import com.tul.ta.service.AirportService;
import com.tul.ta.service.DefaultWeatherService;
import com.tul.ta.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/trip")
public class TripController {
    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private DefaultWeatherService weatherService;

    @Autowired
    private FlightDtoMapper flightDtoMapper;

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ResponseEntity getSchedule(
            @RequestParam(value = "origin") String origin,
            @RequestParam(value = "destination") String destination,
            @RequestParam(value = "departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate
    ) {
        FlightSchedulesDto flightSchedulesDto = this.flightService.getSchedules(AirportCode.valueOf(origin), AirportCode.valueOf(destination), departureDate.toString(), true);
        List<Flight> flights = new ArrayList<>();
        flightSchedulesDto.getScheduleResource().getSchedule().forEach(s -> flights.add(flightDtoMapper.mapToEntity(s.flight)));
        Trip trip = new Trip(flights, weatherService.getWeatherByCityWithDate(airportService.getAirportById(destination).getOneCityName(), departureDate.toString()));
        return ResponseEntity.ok(trip);
    }
}
