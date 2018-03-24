package com.revature.project2.events;

import com.revature.project2.Project2Application;
import com.revature.project2.helpers.Json;
import com.revature.project2.helpers.Seeder;
import com.revature.project2.security.SecurityConstraints;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Project2Application.class)
@SpringBootTest
public class EventControllerTest {

  private MockMvc mockMvc;
  private Event event = new Event();

  @Autowired
  private Seeder seeder;

  @Autowired
  private WebApplicationContext wac;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    event = seeder.makeEvent(seeder.makeUser());
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
        .andExpect(jsonPath("$.message").exists())
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  public void fetchEvent() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders
            .get("/events/7")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.host").exists())
        .andExpect(jsonPath("$.event").exists())
        .andDo(print());
  }

  @Test
  public void fetchAllEvents() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.
        get("/events"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();
  }

  @Test
  public void fetchEventById() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.
        get("/events/{event_id}", 7))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();
  }

  @Test//Method in EventController still needs to be implemented, and then tested
  public void updateEventById() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.
        put("/api/events/{event_id}", 7))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();
  }
}