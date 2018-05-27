package ru.iac.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import ru.iac.domain.ListTable;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MainController mainController;

    @Test
    public void testShowMainPageGet() throws Exception {
        given(mainController.showMainPage(new ModelMap())).willReturn("main");
        this.mvc.perform(get("/main")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(mainController).showMainPage(new ModelMap());
        verifyNoMoreInteractions(mainController);
    }

    @Test
    public void testShowMainPagePost() throws Exception {
        given(mainController.add("path", new ModelMap())).willReturn("main");
        this.mvc.perform(post("/main").param("path","path")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(mainController).add("path", new ModelMap());
        verifyNoMoreInteractions(mainController);
    }

    @Test
    public void testGetListPaths() throws Exception {
        List<ListTable> list = new ArrayList<>();
        given(mainController.listPaths("0")).willReturn(list);
        this.mvc.perform(get("/json").param("id", "0")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(mainController).listPaths("0");
        verifyNoMoreInteractions(mainController);
    }
}