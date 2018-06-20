package com.tul.ta.controller;


import com.tul.ta.model.weather.Weather;
import com.tul.ta.service.DefaultWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private static final Logger logger = LoggerFactory.getLogger(DefaultWeatherService.class);

    @Autowired
    DefaultWeatherService weatherService;

    @RequestMapping(value = "/{city}", method = RequestMethod.GET)
    public Weather getWeatherByCity(@PathVariable(value = "city") String city) {
        logger.info("Received request to get current weather for city {}", city);
        return weatherService.getWeatherByCity(city);
    }

    @RequestMapping(value = "/{city}", method = RequestMethod.GET, produces = "application/json", params = "date")
    public Weather getWeatherByCityWithDate(@PathVariable(value = "city") String city,
                                            @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        logger.info("Received request to get weather for city {} with date {}", city, date);
        return weatherService.getWeatherByCityWithDate(city, date.toString());
    }
}
