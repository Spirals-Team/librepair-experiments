package com.revature.project2.users;

import com.revature.project2.Project2Application;
import com.revature.project2.helpers.Seeder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for user controller
 */
//@TestPropertySource(locations = "classpath:application-test.properties")//Loads the connection file
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Project2Application.class)
@SpringBootTest
public class UserControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private Seeder seed;

  @Autowired
  private WebApplicationContext wac;

  private Map<String, String> credentials = new HashMap<>();

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;


  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void fetchAllUsers() throws Exception {
    mockMvc.perform(
        get("/users"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();
  }

  @Test
  public void findByUserId() throws Exception {
    mockMvc.perform(
        get("/users/{id}", 1))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();
  }

  @Test
  public void updateUser() throws Exception {
    //Creates User
    User user = new User();
    user.setId(2);
    user.setFirstName("Nick");
    user.setLastName("Ralph");
    user.setUsername("nr");
    user.setPassword("pass");

    User user2 = new User();
    user.setId(3);
    user.setFirstName("Sarah");
    user.setLastName("Dubb");
    user.setUsername("sb");
    user.setPassword("pass");

    //When a userService receives a call on its saved method for any User class, return the mock user.
    Mockito.when(userService.updateUser(any(User.class))).thenReturn(user);

    // Save the user
    User newUser = userService.updateUser(user);

    System.out.println(newUser);

    // Verify the save
    assertNotEquals("Nick", newUser.getFirstName());
    assertNotEquals("Ralph", newUser.getLastName());

    mockMvc.perform(post("/api/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
            "  \"id\": 1,\n" +
            "  \"username\": \"admin\",\n" +
            "  \"firstName\": \"Johnny\",\n" +
            "  \"lastName\": \"Gazorpazorp\",\n" +
            "  \"email\": \"Jessica.Gazorpazorp@example.com\",\n" +
            "  \"dateOfBirth\": \"1992-02-12\",\n" +
            "  \"placeId\": \"Eik1ODQ1IExlZXNidXJnIFBpa2UsIFZpZW5uYSwgVkEgMjIxODIsIFVTQQ\",\n" +
            "  \"admin\": true,\n" +
            "  \"flagged\": false,\n" +
            "  \"location\": \"5845 Leesburg Pike, Vienna, VA, USA\"\n" +
            "}"))
        .andExpect(status().isOk())
        .andDo(print());

    mockMvc.perform(get("/users/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.firstName").value("Johnny"))
        .andDo(print());
  }
  

  @Test
  public void cancelEvent() throws Exception {
    mockMvc.perform(
            get("/api/events/cancel/{event_id}/user/{user_id}", 7, 1))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
  }

  @Test
  public void isAttend() throws Exception {
    mockMvc.perform(
            get("/api/events/attend/{event_id}/user/{user_id}",  7, 1))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
  }

  @Test
  public void updatePassword() throws Exception {
    mockMvc.perform(post("/api/users/{id}/password", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" + "\"password\"" + ":" + "\"secrets\"" + "}"))
            .andExpect(status().isOk())
            .andDo(print());

  }

}