package ua.epam.spring.hometask.web.rest;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ua.epam.spring.hometask.domain.Ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class BookingRestControllerLiveTest extends AbstractRestLiveTest {

    private RestTemplate restTemplate;


    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());
    }

    @Test
    public void test1_getPurchasedTicketsInJson() {
        String url = "http://localhost:8080/movie/api/tickets/purchased/{eventId}/{airDate}";


        //Somehow we need to make restTemplate logged on

//        ResponseEntity<List<Ticket>> ticketList = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Ticket>>() {
//                },
//                0,
//                LocalDateTime.of(2018, 1, 1, 10, 0));//2018-01-01T10:00
//
//
//        Assert.assertEquals(2, ticketList.getBody().size());

    }

    @Test
    public void test2_getPurchasedTicketsInPdf() {

        String url = "http://localhost:8080/movie/api/tickets/purchased/{eventId}/{airDate}";

    }
}