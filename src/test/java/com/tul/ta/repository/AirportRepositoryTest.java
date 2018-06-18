package com.tul.ta.repository;

import com.tul.ta.exception.ResourceNotFoundException;
import com.tul.ta.model.airport.Airport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AirportRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AirportRepository airportRepository;

    @Test
    public void whenFindByAirportCodeThenReturnAirport() {
        //given
        Airport AAL = Airport.builder()
                .airportCode("AAL")
                .cityCode("AAL")
                .countryCode("DK")
                .utcOffset(2)
                .timeZoneId("European/Copenhagen")
                .build();

        entityManager.persist(AAL);
        entityManager.flush();

        //when
        Airport found = airportRepository.findById(AAL.getAirportCode())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", AAL.getAirportCode()));

        //then
        Assert.assertEquals(found.getAirportCode(), AAL.getAirportCode());
    }
}
