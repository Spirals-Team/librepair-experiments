package com.d4dl.hellofib.integration.greeting;

import com.d4dl.hellofib.greeting.Greeting;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Utility to create greeting using the create endpoint.
 */
public class GreetingEndpointUtility {

    /**
     * Use the restTemplate to post a Greeting to the greeting endpoint.
     * @param restTemplate the spring template used to post the request.
     * @param port the port of the test server.  The <code>@LocalPort</code> annotation can be used to wire in the
     *             test server port.
     * @param name the name of the greeter for the greeting
     * @param world the name of the world for the greeting.
     * @return the entity wrapping the response
     */
    public ResponseEntity<Greeting> postAGreeting(TestRestTemplate restTemplate, String port, String name, String world) {
        HttpHeaders headers = new HttpHeaders();
        Greeting helloMars = new Greeting(name, world);
        HttpEntity<Greeting> marsRequest = new HttpEntity(helloMars, headers);
        return restTemplate.exchange("http://localhost:" + port + "/greetings", HttpMethod.POST, marsRequest, Greeting.class);
    }
}
