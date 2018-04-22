package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.TestUtils;
import ar.edu.undav.semillero.domain.entity.Company;
import ar.edu.undav.semillero.domain.entity.Node;
import ar.edu.undav.semillero.request.CreateCompanyRequest;
import ar.edu.undav.semillero.request.CreateNodeRequest;
import ar.edu.undav.semillero.service.NodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NodeController.class)
@Import(SpringDataWebAutoConfiguration.class)
public class NodeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private NodeService nodeService;

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(nodeService);
    }

    // POST Tests
    @Test
    public void saveNodeNotPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/node"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    public void saveNodePost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/node")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void saveNodePostParams() throws Exception {
        String name = "Juan";
        String address = "sacacorcho 123";
        CreateNodeRequest request = new CreateNodeRequest(name, address);
        mockMvc.perform(MockMvcRequestBuilders.post("/node").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(nodeService).save(Mockito.eq(request));
    }

    @Test
    public void saveNodePostParamsInvalid() throws Exception {
        CreateNodeRequest request = new CreateNodeRequest(null, null);
        mockMvc.perform(MockMvcRequestBuilders.post("/node").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateNode() throws Exception {
        CreateNodeRequest request = new CreateNodeRequest("Banfield", "Morazán 669");
        Node node = new Node(request.getName(), request.getAddress());
        TestUtils.setId(node, 1L);
        Mockito.when(nodeService.update(anyLong(), any(CreateNodeRequest.class))).thenReturn(Optional.of(node));
        mockMvc.perform(MockMvcRequestBuilders.put("/node/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue(Number.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.notNullValue(String.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.notNullValue(String.class)));
        Mockito.verify(nodeService).update(Mockito.eq(1L), Mockito.eq(request));
    }

    // GET Tests

    @Test
    public void getAllNodes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/node"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(nodeService).findAll();
    }

    @Test
    public void getNode() throws Exception {
        Mockito.when(nodeService.findById(anyLong())).thenReturn(Optional.of(new Node()));
        mockMvc.perform(MockMvcRequestBuilders.get("/node/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(nodeService).findById(Mockito.eq(1L));
    }

    @Test
    public void getNotExistingNode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/node/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        Mockito.verify(nodeService).findById(Mockito.eq(999L));
    }
}
