package com.epam.test_generator.api.steps;

import com.epam.test_generator.api.BddGeneratorApi;
import cucumber.api.java.Before;

import static com.epam.http.requests.ServiceInit.init;

public class Hooks {

    @Before
    public void beforeScenario() {
        init(BddGeneratorApi.class);
    }
}
