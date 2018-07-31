package tech.spring.structure.auth;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tech.spring.structure.auth.model.User;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthIntegrationTest extends AuthIntegrationHelper {

    @Test
    public void testLoginUser() throws Exception {
        testLogin(getMockUser("user"));
    }

    @Test
    public void testLogoutUser() throws Exception {
        testLogout(getMockUser("user"));
    }

    @Test
    public void testLoginAdmin() throws Exception {
        testLogin(getMockUser("admin"));
    }

    @Test
    public void testLogoutAdmin() throws Exception {
        testLogout(getMockUser("admin"));
    }

    @Test
    public void testLoginSuperadmin() throws Exception {
        testLogin(getMockUser("superadmin"));
    }

    @Test
    public void testLogoutSuperadmin() throws Exception {
        testLogout(getMockUser("superadmin"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        // @formatter:off
        mockMvc.perform(post("/login").param("username", "admin").param("password", "IncorrectPassword321~"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Bad credentials")));
        // @formatter:on
    }

    @Test
    public void testGetUserAsUser() throws Exception {
        testGetUser(getMockUser("user"));
    }

    @Test
    public void testGetUserAsAdmin() throws Exception {
        testGetUser(getMockUser("admin"));
    }

    @Test
    public void testGetUserAsSuperadmin() throws Exception {
        testGetUser(getMockUser("superadmin"));
    }

    @Test
    public void testGetUserAsAnonymous() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/user"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Full authentication is required to access this resource")));
        // @formatter:on
    }

    private void testLogin(User user) throws Exception {
        // @formatter:off
        mockMvc.perform(post("/login").param("username", user.getUsername()).param("password", user.getPassword()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("active", equalTo(user.isActive())))
            .andExpect(jsonPath("enabled", equalTo(user.isEnabled())))
            .andExpect(jsonPath("username", equalTo(user.getUsername())))
            .andExpect(jsonPath("role", equalTo(user.getRole().toString())));
        // @formatter:on
    }

    private void testLogout(User user) throws Exception {
        // @formatter:off
        mockMvc.perform(post("/logout").cookie(login(user)))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("Logout success")));
        // @formatter:on
    }

    private void testGetUser(User user) throws Exception {
     // @formatter:off
        mockMvc.perform(get("/user").cookie(login(user)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("active", equalTo(user.isActive())))
            .andExpect(jsonPath("enabled", equalTo(user.isEnabled())))
            .andExpect(jsonPath("username", equalTo(user.getUsername())))
            .andExpect(jsonPath("role", equalTo(user.getRole().toString())));
        // @formatter:on
    }

}
