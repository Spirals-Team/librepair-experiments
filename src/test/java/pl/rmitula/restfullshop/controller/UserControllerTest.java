package pl.rmitula.restfullshop.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void GetAllUsersTest() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].firstName", Matchers.is("Andrzej")))
                .andExpect(jsonPath("$.[0].lastName", Matchers.is("Duda")))
                .andExpect(jsonPath("$.[0].username", Matchers.is("admin")))
                .andExpect(jsonPath("$.[0].email", Matchers.is("aduda@gmail.com")))
                .andExpect(jsonPath("$.[1].firstName", Matchers.is("Rafal")))
                .andExpect(jsonPath("$.[1].lastName", Matchers.is("Mitula")))
                .andExpect(jsonPath("$.[1].username", Matchers.is("rmitula")))
                .andExpect(jsonPath("$.[1].email", Matchers.is("rmitula@gmail.com")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(3)));
    }

    @Test
    public void GetUserByIdTest() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", Matchers.is("Andrzej")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Duda")))
                .andExpect(jsonPath("$.username", Matchers.is("admin")))
                .andExpect(jsonPath("$.email", Matchers.is("aduda@gmail.com")));
    }

    @Test
    public void GetUserByIdWithUnknownIdTest() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 100))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found user with id: 100")));
    }

    @Test
    public void GetUserByUserNameTest() throws Exception {
        mockMvc.perform(get("/api/users/findByUserName/{name}", "admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", Matchers.is("Andrzej")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Duda")))
                .andExpect(jsonPath("$.username", Matchers.is("admin")))
                .andExpect(jsonPath("$.email", Matchers.is("aduda@gmail.com")));
    }

    @Test
    public void GetUserByUserNameWithIgnoreCaseTest() throws Exception {
        mockMvc.perform(get("/api/users/findByUserName/{name}", "aDmIN"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.firstName", Matchers.is("Andrzej")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Duda")))
                .andExpect(jsonPath("$.username", Matchers.is("admin")))
                .andExpect(jsonPath("$.email", Matchers.is("aduda@gmail.com")));
    }

    @Test
    public void GetUserByUnknownUserNameTest() throws Exception {
        mockMvc.perform(get("/api/users/findByUserName/{name}", "unknown"))
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found user with username: unknown")));
    }

    /* ARCHIVE
    "...So MockMvc tests simply aren't enough to test error responses generated through Spring Boot...."
    https://github.com/spring-projects/spring-boot/issues/7321

    @Test
    public void GetUserByIdWithUnknownIdTest() throws Exception {
        when(userService.findById(1)).thenThrow(new NotFoundException("Not found user with id: 1"));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isBadRequest());
    }
    */

    @Test
    public void CreateUserTest() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("Tyrion", "Lanister", "tyrion", "tyrion@lanister.pl", "password")))
                .andExpect(status().isCreated());
    }

    @Test
    public void CreateUserWithExistingUserNameTest() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("Adam", "Kowalski", "admin", "adamkowalski@gmail.com", "password")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.is(409)))
                .andExpect(jsonPath("$.description", Matchers.is("This username or email is already associated with a different user account.")));
    }

    @Test
    public void CreateUserWithExistingEmailTest() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("Adam", "Kowalski", "aDAm", "aduda@gmail.com", "password")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.is(409)))
                .andExpect(jsonPath("$.description", Matchers.is("This username or email is already associated with a different user account.")));
    }

    @Test
    public void CreateUserWithoutOneFieldlTest() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                //Same as createUserInJson but without LastName field.
                .content("{ \"firstName\": \"Adam\", " +
                        "\"email\":\"akowalski@gmail.com\"," +
                        "\"username\":\"akowalski\"," +
                        "\"password\":\"password\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is(400)));
    }

    @Test
    public void UpdateUserDetailsTest() throws Exception {
        mockMvc.perform(put("/api/users/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("John", "Lanister", "tyrfion", "tyfrion@lanister.pl", "password")))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateUserNameWithAnotherExistingUserNameTest() throws Exception {
        mockMvc.perform(put("/api/users/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("John", "Lanister", "admin", "tyfrion@lanister.pl", "password")))
                .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.code", Matchers.is(409)))
                        .andExpect(jsonPath("$.description", Matchers.is("This username is already associated with another user account.")));
    }

    @Test
    public void UpdateUserEmailWithAnotherExistingEmailTest() throws Exception {
        mockMvc.perform(put("/api/users/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("John", "Lanister", "another", "aduda@gmail.com", "password")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.is(409)))
                .andExpect(jsonPath("$.description", Matchers.is("This email is already associated with another user account.")));
    }

    @Test
    public void UpdateUserWithUnknownIdTest() throws Exception {
        mockMvc.perform(put("/api/users/{id}", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("John", "Lanister", "another", "aduda@gmail.com", "password")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found user with id: 100")));
    }

    @Test
    public void DeleteUserTest() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void DeleteUserWithUnknownIdTest() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 100)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found user with id: 100")));
    }

    private static String createUserInJson (String firstName, String lastName, String userName, String email, String password) {
        return "{ \"firstName\": \"" + firstName + "\", " +
                "\"lastName\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"username\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"}";
    }
}