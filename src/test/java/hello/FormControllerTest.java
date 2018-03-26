package hello;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import uniovi.es.Application;
import uniovi.es.entities.Agent;
import uniovi.es.services.AgentsService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("repository")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class FormControllerTest {
    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AgentsService agentsService;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        template = new TestRestTemplate();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testLoginPage() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username:")))
                .andExpect(content().string(containsString("Password:")))
                .andExpect(content().string(containsString("Kind:")));
    }

    @Test
    public void testLoginCorrect() throws Exception {
//        UserInfo user = new UserInfo("pass", "name", "surname", "macorrect@il.com", new Date());
//        db.insertUser(user);
//
//        mockMvc.perform(post("/login")
//                .param("login", "macorrect@il.com")
//                .param("password", "pass"))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("name1", equalTo("name")))
//                .andExpect(model().attribute("name2", equalTo("surname")))
//                .andExpect(model().attribute("email", equalTo("macorrect@il.com")))
//                .andExpect(content().string(containsString("Name:")))
//                .andExpect(content().string(containsString("Birthdate:")));
    	
    	//UserInfo2 user = new UserInfo2("11111111A","123456","Pepe","mail@mail.com","Oviedo","Person","1");
    	Agent user = new Agent("11111111A","123456","Pepe","mail@mail.com","Oviedo","Person");
    	agentsService.deleteUser(user);
    	agentsService.insertUser(user);
    	
    	mockMvc.perform(post("/login")
              .param("login", "11111111A")
              .param("password", "123456")
    		  .param("kind","Person"))
              .andExpect(status().isOk())
              .andExpect(model().attribute("name", equalTo("Pepe")))
              .andExpect(model().attribute("email", equalTo("mail@mail.com")))
              .andExpect(model().attribute("location", equalTo("Oviedo")))
              .andExpect(model().attribute("kind", equalTo("Person")))
              .andExpect(model().attribute("kindCode", equalTo("1")));
    	
    	
    }

    @Test
    public void testLoginIncorrect() throws Exception {
//        mockMvc.perform(post("/login")
//                .param("login", "inco@rre.ct")
//                .param("password", "user"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Invalid login details.")));
    	 mockMvc.perform(post("/login")
               .param("login", "11111111A")
               .param("password", "123456")
               .param("kind","Agent"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Invalid login details.")));
    }

}