package com.tul.ta.util;

import com.tul.ta.client.ApiAuthentication;
import com.tul.ta.model.schedule.Flight;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class HttpQueryUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpQueryUtilsTest.class);

    private ApiAuthentication apiAuthenticationMock = Mockito.mock(ApiAuthentication.class);

    private RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);

    private HttpQueryUtils httpClient = new HttpQueryUtils();

    private URI uri = URI.create("http://localhost");

    @Before
    public void init() {
        ResponseEntity<Flight> myEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Mockito.when(restTemplateMock.exchange(
                ArgumentMatchers.eq(uri.toString()),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.<HttpEntity<Flight>>any(),
                ArgumentMatchers.<Class<Flight>>any()
        )).thenReturn(myEntity);
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldRetryToRefreshToken() {
        Mockito.when(apiAuthenticationMock.updateAccessToken()).thenReturn(true);

        int maxRetried = 3;
        try {
            httpClient.executeQuery(uri.toString(), Flight.class, apiAuthenticationMock, restTemplateMock);
        } finally {
            Mockito.verify(apiAuthenticationMock, Mockito.times(maxRetried)).updateAccessToken();
        }
    }
}
