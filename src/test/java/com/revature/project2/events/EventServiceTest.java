package com.revature.project2.events;

import com.revature.project2.Project2Application;
import com.revature.project2.helpers.Seeder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Project2Application.class)
@SpringBootTest
public class EventServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private Seeder seed;

    @Autowired
    private WebApplicationContext wac;

    private Map<String, String> credentials = new HashMap<>();

    @Test
    public void aTest() {

    }

}
