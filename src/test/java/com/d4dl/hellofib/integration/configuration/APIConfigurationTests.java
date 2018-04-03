package com.d4dl.hellofib.integration.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test that make sure beans supporting the API document generation are loaded.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class APIConfigurationTests {

    @Autowired
    private Docket api;

    /**
     * Make sure the api beans have been configured.
     */
    @Test
    public void whenContextLoadsBeansExist() {
        assertThat(api).isNotNull();
    }
}
