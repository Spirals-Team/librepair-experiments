package controller;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kafka.sender.Sender;
import main.Application;
import model.Incidence;
import model.Operator;
import repository.IncidenceRepository;
import repository.OperatorRepository;
import utils.IncidenceUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "8090" })
public class ControllerTest {
	

	  @Autowired
	  private WebApplicationContext context;

	  private MockMvc mvc;
	  
	    @Autowired
	    private Sender sender;
	    @Autowired
	    private IncidenceRepository inciRepository;
	    @Autowired
	    private OperatorRepository operatorRepository;

	  @Before
	  public void setUp() throws Exception {
	    mvc = MockMvcBuilders.webAppContextSetup(context).build();
	  }


	  @Test
	  public void testIndex() throws Exception {
	    mvc.perform(get("/index")).andExpect(status().isOk()).andExpect(content().string(containsString("Dashboard_i1b")));
	  }
	  
	  @Test
	  public void testIndexLogin() throws Exception {
		  mvc.perform(get("/index/login?id=operator1&pass=asd")).andExpect(status().isFound());
	  }
	  
	  @Test
	  public void testManage() throws Exception {
		Incidence inci = new Incidence();
		inci.setInciId("id1");
      	sender.send(inci);
      	inciRepository.save(inci);
      	if(inciRepository.count()>0)
      		System.err.println("Incidence saved");
      	Operator o1 = new Operator("operator1", "asd");
      	OperatorController.loggedOperator=o1;
	    mvc.perform(get("/manage/id1")).andExpect(content().string(containsString("Incidence Manager")));
	  }
	  @Test
	  public void testManageReturn() throws Exception {
		  mvc.perform(post("/manage/return")).andExpect(status().isFound());
	  }
	}
