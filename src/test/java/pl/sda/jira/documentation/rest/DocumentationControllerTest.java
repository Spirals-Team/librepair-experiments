package pl.sda.jira.documentation.rest;

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
import pl.sda.jira.documentation.domain.Documentation;
import pl.sda.jira.documentation.domain.DocumentationRepository;
import pl.sda.jira.documentation.domain.exception.DocumentDoestExist;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DocumentationControllerTest {
    @Autowired
    private MockMvc chrome;
    @Autowired
    private DocumentationRepository documentationRepository ;

    final private long ID = 13L;
    final private String TITLE = "JIRA";
    final private Documentation DOCUMENTATION = new Documentation(ID, TITLE);


    @Test
    public void shouldDelete() throws Exception {
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.delete("/document/13")
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test(expected = DocumentDoestExist.class)
    public void shouldNotDeleteWhenDocumentNotExist() throws UnsupportedEncodingException {

        MockHttpServletResponse response = null;
        try {
            response = chrome.perform(
                    MockMvcRequestBuilders.delete("/document/13")
            ).andReturn().getResponse();
        } catch (Exception e) {
            throw new DocumentDoestExist(ID);
        }

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Document doest exist . About this id : 13" , response.getContentAsString());
    }

    @Test
    public void shouldGet() throws Exception {
        documentationRepository.add(DOCUMENTATION);
        MockHttpServletResponse response = aDocumentBy("13");

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"title\":\"" + TITLE + "\"}", response.getContentAsString());

    }

    private MockHttpServletResponse aDocumentBy(String id) throws Exception {
        return chrome.perform(
                    MockMvcRequestBuilders.get("/document/" + id)
            ).andReturn().getResponse();
    }

    @Test(expected = DocumentDoestExist.class)
    public void shouldNotGetWhenDocumentNotExist() throws UnsupportedEncodingException {
        MockHttpServletResponse response = null;
        try {
            response = chrome.perform(
                    MockMvcRequestBuilders.get("/document/13")
            ).andReturn().getResponse();
        } catch (Exception e) {
            throw new DocumentDoestExist(ID);
        }

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Document doest exist . About this id : 13" , response.getContentAsString());

    }
    @Test
    public void shouldCreate() throws Exception {
        MockHttpServletResponse response = chrome.perform(
                MockMvcRequestBuilders.put("/document")
                    .param("title", TITLE)
                ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String id = response.getContentAsString();
        assertEquals("{\"title\":\"" + TITLE + "\"}", aDocumentBy(id).getContentAsString());

  }
}
