package uniovi.es;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import uniovi.es.entities.Agent;
import uniovi.es.services.AgentsService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class APIControllerTest {

    private MockMvc mockMvc;
    
    // Log
    private static final Logger LOG = LoggerFactory.getLogger(APIControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AgentsService agentsService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void postTestUser() throws Exception {
    	Agent user = new Agent("11111111A","123456","Pepe","pepe@gmail.com","Oviedo","Person","1");
		  agentsService.deleteUser(user);
		  agentsService.insertUser(user);
		  try {
		  mockMvc.perform(post("/checkAgent")
		          .content("{ \"login\": \"11111111A\", \"password\": \"123456\", \"kindCode\": \"1\"}")
		          .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
		                  MediaType.APPLICATION_JSON.getSubtype(),
		                  Charset.forName("utf8"))))
		          .andExpect(status().isOk())
		          .andExpect(content().contentType("application/json;charset=UTF-8"))
	              .andExpect(content().encoding("UTF-8"))
	              .andExpect(content().json("{\"name\":\"Pepe\",\"location\":\"Oviedo\",\"email\":\"pepe@gmail.com\","
	              		+ "\"id\":\"11111111A\",\"kind\":\"Person\",\"kindCode\":\"1\"}")
	                );
		  }catch(Exception e)
		  {
			  LOG.error(e.getMessage(), e);
		  }
    }

    @Test
    public void postTestNotFoundUser() throws Exception {
        mockMvc.perform(post("/checkAgent")
                .content("{ \"login\": \"11111111A\", \"password\": \"123456\", \"kindCode\": \"3\"}")
                .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                        MediaType.APPLICATION_JSON.getSubtype(),
                        Charset.forName("utf8"))))
                .andExpect(status().isNotFound()
                );

    }


}
