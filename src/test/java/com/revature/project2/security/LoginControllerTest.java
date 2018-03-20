package com.revature.project2.security;

import com.revature.project2.Project2Application;
import com.revature.project2.helpers.Json;
import com.revature.project2.helpers.Seeder;
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

  @Autowired
  private Seeder seed;

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
        .andDo(print());
  }

    @Test
    public void isExist() throws Exception {
        User user = seed.makeUser(false);
        String username = "admin";

        mockMvc.perform(MockMvcRequestBuilders.
                get("/users/check/" + user.getUsername()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

    @Test
    public void isExistEmail() throws Exception {
        User user = seed.makeUser(false);
        String useremail = "jn@gmail.com";

        mockMvc.perform(MockMvcRequestBuilders.
                get("/users/checkemail/{email}", useremail))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.
                get("/users/checkemail/{email}", user.getEmail()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

  @Test
  public void signUp() throws Exception {
    User user = seed.makeUser(false);
    mockMvc.perform(
        MockMvcRequestBuilders
            .post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.toJson(user))
    )
        .andExpect(status().isOk())
        .andDo(print());

  }

}