package com.tul.ta.service;

import com.tul.ta.exception.ResourceNotFoundException;
import com.tul.ta.model.airport.Airport;
import com.tul.ta.repository.AirportRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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
    private AirportRepository airportRepositoryMock;

    @Autowired
    private AirportService airportService;

    @Test
    public void serviceClasspathShouldBeCorrect() {
        assertEquals("class com.tul.ta.service.DefaultAirportService", airportService.getClass().toString() );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenValidAirportCodeThenAirportShouldBeFound() {
        Airport AAL = Airport.builder()
                .airportCode("AAL")
                .cityCode("AAL")
                .countryCode("DK")
                .utcOffset(2)
                .timeZoneId("European/Copenhagen")
                .build();

        when(airportRepositoryMock.findById(AAL.getAirportCode())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", "AAL")))
                .thenReturn(AAL);

        String airportCode = "AAL";
        Airport found = airportService.getAirportById(airportCode);

        assertEquals(found.getAirportCode(), airportCode);
    }

    @Test
    public void findAllShouldReturnListOfAirportEntries() {
        List<Airport> airports = new ArrayList<>();
        when(airportRepositoryMock.findAll()).thenReturn(airports);

        List<Airport> actual = airportService.getAll();

        verify(airportRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(airportRepositoryMock);

        assertThat(actual, is(airports));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findAirportByIdShouldThrowNotFoundException() throws ResourceNotFoundException {
        String airportCode = "AAL";
        when(airportRepositoryMock.findById(airportCode)).thenThrow(new ResourceNotFoundException("Airport", "id", airportCode));

        airportService.getAirportById(airportCode);

        verify(airportRepositoryMock, times(1)).findById(airportCode);
        verifyNoMoreInteractions(airportRepositoryMock);
    }
}
