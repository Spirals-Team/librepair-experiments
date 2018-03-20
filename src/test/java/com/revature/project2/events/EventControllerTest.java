package com.revature.project2.events;

import com.revature.project2.Project2Application;
import com.revature.project2.helpers.Json;
import com.revature.project2.helpers.Seeder;
import com.revature.project2.security.SecurityConstraints;
import com.revature.project2.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Project2Application.class)
@SpringBootTest
public class EventControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private Seeder seed;

  @Autowired
  private WebApplicationContext wac;

  private Map<String, String> credentials = new HashMap<>();

  @After
  public void tearDown() throws Exception {
  }

  private Event event;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    event = Event.builder().title("title").startDateTime(new Date()).endDateTime(new Date()).description("description").cost(50).maxAttendees(50).minimumAge(18).guestsAllowed(0).build();
  }

  @Test
  public void createEvents() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/api/events")
            .contentType(MediaType.APPLICATION_JSON)
            .header(
                "Authorization",
                "Bearer " + SecurityConstraints.generateToken("admin")
            )
            .content(Json.toJson(event)))
        .andExpect(jsonPath("$.cost").value(50))
        .andExpect(jsonPath("$.maxAttendees").value(50))
        .andExpect(jsonPath("$.endDateTime").exists())
        .andDo(print());
  }

  @Test
  public void fetchEventById() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.
            get("/events"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();


  }
}