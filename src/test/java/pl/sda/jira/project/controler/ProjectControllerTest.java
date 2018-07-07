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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc chrome;
    @Autowired
    private ProjectService service;
    private static final String NEW_NAME = "spiderman";

    @Test
    public void shouldGet() throws Exception {
        String name = "peter";
        long id = givenProject(name);

        MockHttpServletResponse response = aProjectBy(id);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"name\":\"" + name + "\"}", response.getContentAsString());
    }

    private MockHttpServletResponse aProjectBy(long id) throws Exception {
        return chrome.perform(
                    MockMvcRequestBuilders.get("/project/{id}", id)
            ).andReturn().getResponse();
    }

    @Test
    public void shouldRemove() throws Exception {
        String name = "endrju";
        long id = givenProject(name);

        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.delete("/project/{id}", id)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private long givenProject(String name) {
        ProjectDto projectDto = new ProjectDto(name);
        return service.add(projectDto);
    }

    @Test
    public void shouldAdd() throws Exception {
        String name = "dżozef";
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.put("/project")
                        .param("name", name)

        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String id = response.getContentAsString();
        MockHttpServletResponse create = aProjectBy(Long.valueOf(id));
        assertEquals("{\"name\":\"" + name + "\"}", create.getContentAsString());
    }

    @Test
    public void shouldUpdate() throws Exception {
        long id = givenProject("michał");
        String newName = "karina";

        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders
                        .post("/project/{id}", id)
                        .param("name", newName)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        MockHttpServletResponse create = aProjectBy(id);
        assertEquals("{\"name\":\"" + newName + "\"}", create.getContentAsString());
    }

}