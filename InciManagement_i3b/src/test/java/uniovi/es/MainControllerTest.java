package uniovi.es;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

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

import uniovi.es.entities.Incidence;
import uniovi.es.repositories.IncidencesRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MainControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private IncidencesRepository incidentsRepository;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
	
	@Test
    public void testGetLoginPage() throws Exception {
		mockMvc.perform(get("/logIn"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Username:")))
        .andExpect(content().string(containsString("Password:")))
        .andExpect(content().string(containsString("KindCode:")));		
	}
	
//	@Test
//    public void testPostLoginPageCorrectKind() throws Exception {
//		mockMvc.perform(post("/logIn")
//	              .param("name", "Pepe")
//	              .param("password", "123456")
//	    		  .param("kind","1"))
//	              .andExpect(status().is3xxRedirection())
//	              .andExpect(redirectedUrl("createIncidence"));	
//	}
	
	@Test
    public void testPostLoginPageWrongKind() throws Exception {
		mockMvc.perform(post("/logIn")
	              .param("name", "Pepe")
	              .param("password", "123456")
	    		  .param("kind","54654"))
	              .andExpect(status().is3xxRedirection())
	              .andExpect(redirectedUrl("logIn"));		
	}
	
//	@Test
//    public void testGetCreateIncidence() throws Exception {
//		mockMvc.perform(get("/createIncidence")
//				.sessionAttr("user", "Pepe")
//				.sessionAttr("kind", "1"))
//        		.andExpect(status().isOk())
//        		.andExpect(content().string(containsString("Create a new incident")))
//        		.andExpect(content().string(containsString("Add new fields to the incident:")))
//        		.andExpect(content().string(containsString("Other fields:")));		
//	}
	
	@Test
    public void testPostCreateIncidence() throws Exception {
//		mockMvc.perform(post("/send")
//				.sessionAttr("user", "Pepe")
//				.sessionAttr("kind", "1"))
//        		.andExpect(status().isOk())
//        		.andExpect(content().string(containsString("Pepe")))
//        		.andExpect(content().string(containsString("1")))
//        		.andExpect(content().string(containsString("User Page")));	
	}
	
	@Test
    public void testGetList() throws Exception {
		
//		Incidence m = new Incidence();
//		
//		m.setName("Pepe");
//		m.setKind(2);
//		m.setAditionalInfo("Grado 2");
//		m.setLocation("Canada");
//		m.setMessage("Peligro");
//		m.setTitle("Incendio");
//		m.setState(1);
//		String [] s1 = {"tag1", "tag2", "tag3", "tag4"};
//	 	m.setTags(s1);
//	 	HashMap<String, String> customFields = new HashMap<String, String>();
//	 	customFields.put("tempMax", "100 grados");
//	 	m.setCustomFields(customFields);
//	 	
//	 	incidentsRepository.save(m);
//		
//		mockMvc.perform(get("/listIncidences")
//				.sessionAttr("user", "Pepe"))
//        		.andExpect(status().isOk())
//        		.andExpect(content().string(containsString("Grado 2")))
//        		.andExpect(content().string(containsString("Canada")))
//        		.andExpect(content().string(containsString("Peligro")))
//        		.andExpect(content().string(containsString("Incendio")))
//        		.andExpect(content().string(containsString("1")))
//        		.andExpect(content().string(containsString("tag1")))
//        		.andExpect(content().string(containsString("tag2")))
//        		.andExpect(content().string(containsString("tag3")))
//        		.andExpect(content().string(containsString("tempMax")))
//        		.andExpect(content().string(containsString("100 grados")))
//        		.andExpect(content().string(containsString("tag4")));	
//		
//		incidentsRepository.delete(m);
	}
	
}