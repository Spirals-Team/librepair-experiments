package tech.spring.structure.scaffold;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;

import tech.spring.structure.scaffold.model.Scaffold;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ScaffoldIntegrationTest extends ScaffoldIntegrationHelper {

    @Test
    public void testGetScaffolding() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/scaffolding"))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        List<Scaffold> scaffolding = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Scaffold>>() {});
        // @formatter:on

        assertScaffolding(getMockScaffoldingAsAnonymous(), scaffolding);
    }

    @Test
    public void testGetScaffoldingAsUser() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/scaffolding").cookie(login(getMockUser("user"))))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        List<Scaffold> scaffolding = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Scaffold>>() {});
        // @formatter:on

        assertScaffolding(getMockScaffoldingAsUser(), scaffolding);
    }

    @Test
    public void testGetScaffoldingAsAdmin() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/scaffolding").cookie(login(getMockUser("admin"))))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        List<Scaffold> scaffolding = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Scaffold>>() {});
        // @formatter:on

        assertScaffolding(getMockScaffoldingAsAdmin(), scaffolding);
    }

    @Test
    public void testGetScaffoldingAsSuperadmin() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/scaffolding").cookie(login(getMockUser("superadmin"))))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        List<Scaffold> scaffolding = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Scaffold>>() {});
        // @formatter:on

        assertScaffolding(getMockScaffoldingAsSuperadmin(), scaffolding);
    }

    @Test
    public void testGetLoginRequestScaffold() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/scaffold").param("model", "LoginRequest"))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        // @formatter:on
        Scaffold loginRequestScaffold = objectMapper.readValue(result.getResponse().getContentAsString(), Scaffold.class);

        assertScaffold(getMockScaffold("LoginRequest"), loginRequestScaffold);
    }

    @Test
    public void testGetMenuItemScaffoldAsAnonymous() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/scaffold").param("model", "MenuItem"))
            .andExpect(content().contentType(TEXT_PLAIN_VALUE_UTF8))
            .andExpect(content().string("You are not authorized for MenuItem scaffolding!"))
            .andExpect(status().isUnauthorized());
        // @formatter:on
    }

    @Test
    public void testGetMenuItemScaffoldAsUser() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/scaffold").param("model", "MenuItem").cookie(login(getMockUser("user"))))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        // @formatter:on
        Scaffold menuItemScaffold = objectMapper.readValue(result.getResponse().getContentAsString(), Scaffold.class);

        assertScaffold(getMockScaffold("MenuItem"), menuItemScaffold);
    }

    @Test
    public void testGetUnknownScaffold() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/scaffold").param("model", "Unknown"))
            .andExpect(content().contentType(TEXT_PLAIN_VALUE_UTF8))
            .andExpect(content().string("Scaffold for Unknown not found!"))
            .andExpect(status().isNotFound());
        // @formatter:on
    }

}
