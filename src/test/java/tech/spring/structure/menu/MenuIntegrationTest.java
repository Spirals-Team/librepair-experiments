package tech.spring.structure.menu;

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

import tech.spring.structure.menu.model.MenuItem;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MenuIntegrationTest extends MenuIntegrationHelper {

    @Test
    public void testGetMenu() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/menu"))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        List<MenuItem> menu = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<MenuItem>>() {});
        // @formatter:on

        assertMenu(getMockMenuAsAnonymous(), menu);
    }

    @Test
    public void testGetMenuAsUser() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/menu").cookie(login("user")))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        List<MenuItem> menu = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<MenuItem>>() {});
        // @formatter:on

        assertMenu(getMockMenuAsUser(), menu);
    }

    @Test
    public void testGetMenuAsAdmin() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get("/menu").cookie(login("admin")))
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        List<MenuItem> menu = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<MenuItem>>() {});
        // @formatter:on

        assertMenu(getMockMenuAsAdmin(), menu);
    }

}
