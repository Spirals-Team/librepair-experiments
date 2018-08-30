package ru.job4j;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/hibernate-data-context.xml")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class BaseTest {

    @Test
    @Ignore
    public void stubTest() {

    }
}
