package tech.spring.structure.auth;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserIntegrationTest extends AuthIntegrationHelper {

    @Test
    public void testGetUsersAsUser() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/users").cookie(login("user")))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Access is denied")));
        // @formatter:on
    }

    @Test
    public void testGetUsersAsAdmin() throws Exception {
        mockMvc.perform(get("/users").cookie(login("admin"))).andExpect(status().isOk());
    }

    @Test
    public void testGetUsersAsSuperadmin() throws Exception {
        mockMvc.perform(get("/users").cookie(login("superadmin"))).andExpect(status().isOk());
    }

    @Test
    public void testPostUsersAccessDenied() throws Exception {
        // @formatter:off
        mockMvc.perform(post("/users").content("{\"username\":\"user\",\"role\":\"ROLE_USER\"}").cookie(login("superadmin")))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Access is denied")));
        // @formatter:on
    }

    @Test
    public void testPutUserByIdAccessDenied() throws Exception {
        // @formatter:off
        mockMvc.perform(put("/users/1").content("{\"username\":\"user\",\"role\":\"ROLE_USER\"}").cookie(login("superadmin")))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Access is denied")));
        // @formatter:on
    }

    @Test
    public void testGetUserByIdAsUser() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/users/1").cookie(login("user")))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Access is denied")));
        // @formatter:on
    }

    @Test
    public void testGetUserByIdAsAdmin() throws Exception {
        mockMvc.perform(get("/users/1").cookie(login("admin"))).andExpect(status().isOk());
    }

    @Test
    public void testGetUserByIdAsSuperadmin() throws Exception {
        mockMvc.perform(get("/users/1").cookie(login("superadmin"))).andExpect(status().isOk());
    }

    @Test
    public void testPatchUserByIdAsUser() throws Exception {
        // @formatter:off
        mockMvc.perform(patch("/users/4").content("{\"role\":\"ROLE_ADMIN\"}").cookie(login("user")))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Access is denied")));
        // @formatter:on
    }

    @Test
    public void testPatchUserByIdAsAdmin() throws Exception {
        // @formatter:off
        mockMvc.perform(patch("/users/4").content("{\"role\":\"ROLE_ADMIN\"}").cookie(login("admin")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("role", equalTo("ROLE_ADMIN")))
            .andExpect(jsonPath("active", equalTo(true)))
            .andExpect(jsonPath("enabled", equalTo(true)));
        // @formatter:on
    }

    @Test
    public void testPatchUserByIdAsSuperadmin() throws Exception {
        // @formatter:off
        mockMvc.perform(patch("/users/4").content("{\"active\":false,\"enabled\":false}").cookie(login("superadmin")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("active", equalTo(false)))
            .andExpect(jsonPath("enabled", equalTo(false)));
        // @formatter:on
    }

    @Test
    public void testDeleteUserByIdAsUser() throws Exception {
        // @formatter:off
        mockMvc.perform(delete("/users/1").cookie(login("user")))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(equalTo("Access is denied")));
        // @formatter:on
    }

    @Test
    public void testDeleteUserByIdAsAdmin() throws Exception {
        mockMvc.perform(delete("/users/4").cookie(login("admin"))).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUserByIdAsSuperadmin() throws Exception {
        mockMvc.perform(delete("/users/5").cookie(login("superadmin"))).andExpect(status().isNoContent());
    }

}
