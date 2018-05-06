package uniovi.es;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uniovi.es.entities.Agent;
import uniovi.es.services.AgentsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DatabaseTest {
	
	 @Autowired
	  private AgentsService agentsService;

	 @Test
	    public void testDatabase() throws Exception {
	    	Agent user = new Agent("11111111A","123456","Pepe","mail@mail.com","Oviedo","Person","1");
	        agentsService.deleteUser(user);
	        agentsService.insertUser(user);
	        
	        Agent userFromDB = agentsService.getAgent("11111111A", "123456", "1");
	        assertThat(user.getId(), equalTo(userFromDB.getId()));
	        assertThat(user.getPassword(), equalTo(userFromDB.getPassword()));
	        assertThat(user.getKind(), equalTo(userFromDB.getKind()));  

	    }

}
