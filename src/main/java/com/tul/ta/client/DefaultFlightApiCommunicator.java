package com.tul.ta.client;

import com.tul.ta.dto.airport.FlightAirportsDto;
import com.tul.ta.mapper.AirportDtoMapper;
import com.tul.ta.service.AirportService;
import com.tul.ta.util.HttpQueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

public class DefaultFlightApiCommunicator implements FlightApiCommunicator {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFlightApiCommunicator.class);

    private final static String BASE_API_URL = "https://api.lufthansa.com/v1";

    @Autowired
    private HttpQueryUtils httpClient;

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirportDtoMapper airportDtoMapper;

    //@Scheduled(fixedDelay = 60000)
    @PostConstruct
    @Override
    public void getAirports() {
        for(int i=1; i< 3; i++) {
            int limit = 100 * i;
            int offset = limit - 99;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_API_URL + "/references/airports")
                    .queryParam("lang", "EN")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .queryParam("LHoperated", false);

            try {
                FlightAirportsDto flightAirports = httpClient.executeQuery(builder.toUriString(), FlightAirportsDto.class);
                flightAirports.getAirportResource().getAirports().getAirport().forEach(a -> airportService.save(airportDtoMapper.mapToEntity(a)));
            } catch (HttpClientErrorException e) {
                logger.error(e.toString());
            }
        }
    }
}
