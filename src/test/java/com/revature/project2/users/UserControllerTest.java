package com.revature.project2.users;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * Test class for user controller
 */
@TestPropertySource(locations = "classpath:application-test.properties")//Loads the connection file

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserControllerTest {

  @Mock
  private UserRepository userRepository;

//    @Mock
//    UserService userService;

  @InjectMocks
  private UserService userService;


  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createUsers() throws Exception {
    //Creates User
    User user = new User();
    user.setId(2);
    user.setFirstName("Nick");
    user.setLastName("Ralph");
    user.setUsername("nr");
    user.setPassword("pass");

    //When a userService receives a call on its saved method for any User class, return the mock user.
    Mockito.when(userService.save(any(User.class))).thenReturn(user);

    // Save the user
    User newUser = userService.save(user);

    System.out.println(newUser);

    // Verify the save
    assertEquals("Nick", newUser.getFirstName());
    assertEquals("Ralph", newUser.getLastName());

  }

  @Test
  public void findByUserId() {
    //Creates User
    User user = new User();
    user.setId(2);
    user.setFirstName("Nick");
    user.setLastName("Ralph");
    user.setUsername("nr");
    user.setPassword("pass");

    Mockito.when(userService.findByUserId(anyInt())).thenReturn(java.util.Optional.ofNullable(user));

    assertTrue(userService.findByUserId(user.getId()).isPresent());


  }

  @Test
  public void updateUser() {
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

  @Test
  public void findAllUsersTest() {

  }
}