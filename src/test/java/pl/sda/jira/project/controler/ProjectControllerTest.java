package pl.sda.jira.project.controler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.sda.jira.project.model.ProjectDto;
import pl.sda.jira.project.model.ProjectService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc chrome;
    @Autowired
    private ProjectService service;
    private static final String NAME = "peter";
    private static final String NEW_NAME = "spiderman";


    @Test
    public void shouldReturnHelloWorld() throws Exception {
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.get("/project/hello")).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Hello world!", response.getContentAsString());
    }
    @Test
    public void shouldGet() throws Exception {
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.get("/project/13")
        ).andReturn().getResponse();
        ProjectDto projectDto=new ProjectDto(NAME);
        long id = service.add(projectDto);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Retrieved: 13", response.getContentAsString());
    }
    @Test
    public void shouldRemove() throws Exception {
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.delete("/project/13")
        ).andReturn().getResponse();
        ProjectDto projectDto=new ProjectDto(NAME);
        long id = service.add(projectDto);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Removed: 13", response.getContentAsString());
    }
    @Test
    public void shouldAdd() throws Exception {
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.put("/project")
                        .param("name", NAME)

        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("something", response.getContentAsString());
    }

    @Test
    public void shouldUpdate() throws Exception {
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.put("/project/13/projectName")
                        .param("name", NAME)

        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("something", response.getContentAsString());
    }

}