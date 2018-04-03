package com.d4dl.hellofib.integration.greeting;

import com.d4dl.hellofib.greeting.Greeting;
import com.d4dl.hellofib.greeting.GreetingRepository;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Tests for the {@link Greeting} REST endpoints against the actual server
 * (Created by the spring boot web environment).
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingIntegrationTests {

    @LocalServerPort
    private String port;


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GreetingRepository greetingRepository;

    private GreetingEndpointUtility endpointUtility = new GreetingEndpointUtility();

    @Test
    public void whenContextLoadsBeansExist() {
        assertThat(greetingRepository).isNotNull();
    }

    /**
     * Make sure the POST and GET all endpoints work
     * @throws URISyntaxException
     */
    @Test
    public void whenGreetingsArePostedTheyCanAllBeRetrieved() throws URISyntaxException {
        //Post a greeting
        ResponseEntity<Greeting> postResponse1 = endpointUtility.postAGreeting(restTemplate, port, "Joshua", "Mars");
        assertThat(postResponse1.getStatusCodeValue()).isLessThan(299);

        //Post another greeting
        ResponseEntity<Greeting> postResponse2 = endpointUtility.postAGreeting(restTemplate, port, "Peter", "Earth");
        assertThat(postResponse2.getStatusCodeValue()).isLessThan(299);

        //Retrieve all greetings
        ResponseEntity<Resources<Greeting>> getResponse =
                restTemplate.exchange("http://localhost:" + port + "/greetings", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Resources<Greeting>>() {}, Collections.emptyMap());
        Collection<Greeting> content = getResponse.getBody().getContent();
        ArrayList greetings= Lists.newArrayList(content.iterator());

        //There should be two.  That's all of them.
        assertThat(greetings.size()).isEqualTo(2);
    }

    /**
     * Make sure the GET one endpoint works
     * @throws URISyntaxException
     */
    @Test
    public void whenAGreetingsIsPostedItCanBeRetrieved() throws URISyntaxException {
        //Post a greeting
        ResponseEntity<Greeting> postResponse = endpointUtility.postAGreeting(restTemplate, port, "Joshua", "Mars");
        assertThat(postResponse.getStatusCodeValue()).isLessThan(299);
        Greeting postReponse = postResponse.getBody();

        //Retrieve the greeting by id
        ResponseEntity<Resource<Greeting>> getResponse =
                restTemplate.exchange("http://localhost:" + port + "/greetings/" + postReponse.getId(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Greeting>>() {},
                        Collections.emptyMap());
        Greeting greeting = getResponse.getBody().getContent();

        assertThat(greeting.getGreeterName()).isEqualTo("Joshua");
        assertThat(greeting.getId()).isEqualTo(postReponse);
    }

    /**
     * Make sure the PUT endpoint works
     * @throws URISyntaxException
     */
    @Test
    public void whenAGreetingsIsPostedItCanBeUpdated() throws URISyntaxException {
        ResponseEntity<Greeting> postResponse = endpointUtility.postAGreeting(restTemplate, port, "Joshua", "Mars");
        assertThat(postResponse.getStatusCodeValue()).isLessThan(299);
        Greeting greetingResponse = postResponse.getBody();

        Greeting updateGreeting = new Greeting();
        updateGreeting.setWorldName("Moon");
        updateGreeting.setGreeterName("Lenny");

        //Update using the PUT method
        ResponseEntity<Resource<Greeting>> updateResponse =
                restTemplate.exchange("http://localhost:" + port + "/greetings/"  + greetingResponse.getId(),
                        HttpMethod.PUT, new HttpEntity(updateGreeting, null),
                        new ParameterizedTypeReference<Resource<Greeting>>() {},
                        Collections.emptyMap());
        assertThat(updateResponse.getStatusCodeValue()).isLessThan(299);

        //Retrieve it by id again
        ResponseEntity<Resource<Greeting>> getResponse =
                restTemplate.exchange("http://localhost:" + port + "/greetings/" + greetingResponse.getId(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Greeting>>() {},
                        Collections.emptyMap());

        assertThat(getResponse.getBody().getContent().getWorldName()).isEqualTo("Moon");
        assertThat(getResponse.getBody().getContent().getGreeterName()).isEqualTo("Lenny");
    }

    /**
     * Make sure the DELETE endpoint works
     * @throws URISyntaxException
     */
    @Test
    public void whenAGreetingsIsPostedItCanBeDeleted() throws URISyntaxException {
        ResponseEntity<Greeting> postResponse = endpointUtility.postAGreeting(restTemplate, port, "Joshua", "Mars");
        //Update using the PUT method
        restTemplate.exchange("http://localhost:" + port + "/greetings/"  + postResponse.getBody().getId(),
                HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Resource<Greeting>>() {},
                Collections.emptyMap());

        //Retrieve it by id again
        ResponseEntity<Resource<Greeting>> getResponse =
                restTemplate.exchange("http://localhost:" + port + "/greetings/" + postResponse.getBody().getId(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Greeting>>() {},
                        Collections.emptyMap());

        //It should be gone
        assertThat(getResponse.getStatusCodeValue()).isEqualTo(404);
    }

    /**
     * Make sure the findByGreeterName endpoint works
     * @throws URISyntaxException
    @Test
    public void whenAGreetingsIsPostedItCanBeFound() throws URISyntaxException {
        //Post a greeting
        ResponseEntity<Greeting> postResponse1 = endpointUtility.postAGreeting(restTemplate, port, "Joshua", "Mars");
        assertThat(postResponse1.getStatusCodeValue()).isLessThan(299);

        //Retrieve all greetings
        ResponseEntity<Resources<Greeting>> getResponse =
                restTemplate.exchange("http://localhost:" + port +
                        "/search/findByGreeterName?greeterName=Joshua", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Resources<Greeting>>() {}, Collections.emptyMap());
        assertThat(getResponse.getStatusCodeValue()).isLessThan(299);
        Collection<Greeting> content = getResponse.getBody().getContent();
        ArrayList<Greeting> greetings= Lists.newArrayList(content.iterator());

        //There should be two.  That's all of them.
        assertThat(greetings.size()).isEqualTo(1);
        assertThat(greetings.get(0).getGreeterName()).isEqualTo("Joshua");
    }
     */
}
