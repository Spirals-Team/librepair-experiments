package com.tul.ta.service;

import com.tul.ta.dto.schedule.FlightSchedulesDto;
import com.tul.ta.enums.AirportCode;
import com.tul.ta.util.HttpQueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DefaultFlightService implements FlightService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFlightService.class);

    private final static String BASE_API_URL = "https://api.lufthansa.com/v1";

    @Autowired
    private HttpQueryUtils httpClient;

    @Cacheable
    @Override
    public FlightSchedulesDto getSchedules(AirportCode origin, AirportCode destination, String departureDate, Boolean directFlight) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_API_URL + "/operations/schedules/" + origin + "/" + destination + "/" + departureDate)
                .queryParam("directFlights", directFlight);

        FlightSchedulesDto schedule = new FlightSchedulesDto();
        try {
            schedule = httpClient.executeQuery(builder.toUriString(), FlightSchedulesDto.class);
        } catch (HttpClientErrorException e) {
            logger.error(e.toString());
        }
        return schedule;
    }
}
