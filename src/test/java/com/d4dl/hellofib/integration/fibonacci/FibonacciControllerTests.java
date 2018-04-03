package com.d4dl.hellofib.integration.fibonacci;

import com.d4dl.hellofib.fibonacci.FibonacciController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class tests to make sure the configured beans and Spring environment are working and can be auto wired.
 *
 * Also tests for the {@link FibonacciController} REST endpoints against the actual server
 * (Created by the spring boot web environment).
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FibonacciControllerTests {

    @LocalServerPort
    private String port;

    @Autowired
    private FibonacciController fibonacciController;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Make sure the controller is autowireable and non-null
     */
    @Test
    public void whenContextLoadsSpecificBeansExist() {
        assertThat(fibonacciController).isNotNull();
    }

    /**
     * Make sure the endpoint is returning values as without optional parameters.
     */
    @Test
    public void whenContextLoadsAndOptionaParametersAreOmittedEndpointWorks() {
        BigInteger response = restTemplate.getForObject("http://localhost:" + port + "/fibonacci?iterationCount=5", BigInteger.class);
        assertThat(response).isEqualByComparingTo(BigInteger.valueOf(5));
    }

    /**
     * Make sure the endpoint is returning values as expected.
     */
    @Test
    public void whenContextLoadsEndpointWorks() {
        BigInteger response = restTemplate.getForObject("http://localhost:" + port + "/fibonacci?timeComplexity=EXPONENTIAL&iterationCount=5", BigInteger.class);
        assertThat(response).isEqualByComparingTo(BigInteger.valueOf(5));
    }
}
