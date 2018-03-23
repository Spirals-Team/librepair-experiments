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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    mockMvc.perform(MockMvcRequestBuilders.
        get("/users"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();
  }


  @Test
  public void createUsers() throws Exception {

    User user = seed.makeUser();
    mockMvc.perform(MockMvcRequestBuilders
        .post("/users/create")
        .param("username", user.getUsername())
        .param("password", user.getPassword())
        .param("firstName", user.getFirstName())
        .param("lastName", user.getLastName())
        .param("email", user.getEmail())
        .param("dateOfBirth", String.valueOf(user.getDateOfBirth()))
    )
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void findByUserId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.
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


  }
}