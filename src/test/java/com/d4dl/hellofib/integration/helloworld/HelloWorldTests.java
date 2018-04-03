package com.d4dl.hellofib.integration.helloworld;

import com.d4dl.hellofib.helloworld.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests for the hello world jpa tier
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldTests {

    @LocalServerPort
    private String port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetFrench() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Language", "fr-CH");
        HttpEntity<Message> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/helloWorld", HttpMethod.GET, request, String.class);

        assertThat(response.getStatusCodeValue()).isLessThan(299);
    }

    @Test
    public void testVerbs() {
    }
}
