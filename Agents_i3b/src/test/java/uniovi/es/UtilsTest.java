package uniovi.es;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uniovi.es.entities.Agent;
import uniovi.es.utils.AgentJSON;
import uniovi.es.utils.AgentLogin;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UtilsTest {

	@Test
	public void testAgentJson() throws Exception {
		String name;
		String location;
		String email;
		String id;
		String kind;
		String kindCode;

		id = "11111111A";
		name = "Pepe";
		email = "mail@mail.com";
		location = "Oviedo";
		kind = "Person";
		kindCode = "1";

		Agent agent = new Agent("11111111A", "123456", "Pepe", "mail@mail.com", "Oviedo", "Person", "1");
		AgentJSON agentJson = new AgentJSON(agent);

		assertTrue(agentJson.getName().equals(name));
		assertTrue(agentJson.getLocation().equals(location));
		assertTrue(agentJson.getEmail().equals(email));
		assertTrue(agentJson.getId().equals(id));
		assertTrue(agentJson.getKind().equals(kind));
		assertTrue(agentJson.getKindCode().equals(kindCode));

	}
	
	@Test
	public void testAgentLogin() throws Exception {
		String login;
		String password;
		String kindCode;

		login = "11111111A";
		password="123456";
		kindCode = "1";
		
		AgentLogin agentLogin = new AgentLogin();
		
		agentLogin.setLogin(login);
		agentLogin.setPassword(password);
		agentLogin.setKindCode(kindCode);

		assertTrue(agentLogin.getLogin().equals(login));
		assertTrue(agentLogin.getPassword().equals(password));;
		assertTrue(agentLogin.getKindCode().equals(kindCode));

	}

}
