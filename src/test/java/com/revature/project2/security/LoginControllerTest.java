package com.revature.project2.security;

import com.revature.project2.Project2Application;
import com.revature.project2.helpers.Json;
import com.revature.project2.helpers.Seeder;
import com.revature.project2.users.User;
import com.revature.project2.users.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Project2Application.class)
@SpringBootTest
public class LoginControllerTest {

  private MockMvc mockMvc;
  @Mock
  private LoginController loginController;

  @Autowired
  private Seeder seed;

  @Autowired
  private UserService userService;

  @Autowired
  private WebApplicationContext wac;

  private Map<String, String> credentials = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    credentials.put("username", "admin");
    credentials.put("password", "secret");
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void login() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.toJson(credentials))
    )
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.user").exists())
        .andExpect(jsonPath("$.user.password").value(""))
        .andDo(print());
  }

  @Test
  public void isExist() throws Exception {
    User user = seed.makeUser();
    String username = "admin";

    mockMvc.perform(MockMvcRequestBuilders.
        get("/users/checkUsername/" + user.getUsername()))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

  }

  @Test
  public void isExistEmail() throws Exception {
    User user = seed.makeUser();
    String useremail1 = userService.findByUserId(1).get().getEmail();
    String useremail2 = userService.findByUserId(2).get().getEmail();

    mockMvc.perform(MockMvcRequestBuilders.
        get("/users/checkEmail/{email}", useremail1))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    mockMvc.perform(MockMvcRequestBuilders.
        get("/users/checkEmail/{email}", useremail2))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

  }

  @Test
  public void signUp() throws Exception {
    User user = seed.createUser();
    System.out.println("USER: " + user);
    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.toJson(user))
    )
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.user").exists())
        .andExpect(status().isCreated())
        .andDo(print());
  }
}