package com.tul.ta.controller;

import com.tul.ta.dto.schedule.FlightSchedulesDto;
import com.tul.ta.enums.AirportCode;
import com.tul.ta.mapper.FlightDtoMapper;
import com.tul.ta.model.schedule.Flight;
import com.tul.ta.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class ScheduleController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightDtoMapper flightDtoMapper;

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ResponseEntity getSchedule(
            @RequestParam(value = "origin") String origin,
            @RequestParam(value = "destination") String destination,
            @RequestParam(value = "departureDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate
    ) {
        logger.info(departureDate.toString());
        FlightSchedulesDto flightSchedulesDto = this.flightService.getSchedules(AirportCode.valueOf(origin), AirportCode.valueOf(destination), departureDate.toString(), true);
        List<Flight> flights = new ArrayList<>();
        flightSchedulesDto.getScheduleResource().getSchedule().forEach(s -> flights.add(flightDtoMapper.mapToEntity(s.flight)));

        return ResponseEntity.ok(flights);
    }
}
