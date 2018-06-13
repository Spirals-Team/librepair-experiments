package com.tul.ta.service;

import com.tul.ta.exception.ResourceNotFoundException;
import com.tul.ta.model.airport.Airport;
import com.tul.ta.repository.AirportRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AirportServiceTest {

    @TestConfiguration
    static class AirportServiceTestContextConfiguration {

        @Bean
        public AirportService airportService() {
            return new DefaultAirportService();
        }
    }

    @MockBean
    private AirportRepository airportRepository;

    @Autowired
    private AirportService airportService;

    @Test(expected = ResourceNotFoundException.class)
    public void whenValidAirportCodeThenAirportShouldBeFound() {
        Airport AAL = Airport.builder()
                .airportCode("AAL")
                .cityCode("AAL")
                .countryCode("DK")
                .utcOffset(2)
                .timeZoneId("European/Copenhagen")
                .build();

        Mockito.when(airportRepository.findById(AAL.getAirportCode())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", "AAL")))
                .thenReturn(AAL);

        String airportCode = "AAL";
        Airport found = airportService.getAirportById(airportCode);

        assertEquals(found.getAirportCode(), airportCode);
    }
}
