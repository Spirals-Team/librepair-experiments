package uniovi.es;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import uniovi.es.entities.Agent;
import uniovi.es.services.AgentsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MainControllerTest {
	
	private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AgentsService agentsService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/checkAgentInfo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username:")))
                .andExpect(content().string(containsString("Password:")))
                .andExpect(content().string(containsString("KindCode:")));
    }

    @Test
    public void testLoginCorrect() throws Exception {
    	Agent user = new Agent("11111111A","123456","Pepe","mail@mail.com","Oviedo","Person","1");
    	agentsService.deleteUser(user);
    	agentsService.insertUser(user);
    	
    	mockMvc.perform(post("/checkAgentInfo")
              .param("login", "11111111A")
              .param("password", "123456")
    		  .param("kind","1"))
              .andExpect(status().isOk())
              .andExpect(model().attribute("name", equalTo("Pepe")))
              .andExpect(model().attribute("email", equalTo("mail@mail.com")))
              .andExpect(model().attribute("location", equalTo("Oviedo")))
              .andExpect(model().attribute("kind", equalTo("Person")))
              .andExpect(model().attribute("kindCode", equalTo("1")));
	
    }

    @Test
    public void testLoginIncorrect() throws Exception {
    	 mockMvc.perform(post("/checkAgentInfo")
               .param("login", "11111111A")
               .param("password", "123456")
               .param("kind","4"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Invalid agent details.")));
    }

}