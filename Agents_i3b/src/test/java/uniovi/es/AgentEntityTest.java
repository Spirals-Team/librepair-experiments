package uniovi.es;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uniovi.es.entities.Agent;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AgentEntityTest {

	@Test
	public void testUserInfo() throws Exception {
		String id;
		String password;
		String name;
		String email;
		String location;
		String kind;
		String kindCode;

		id = "11111111A";
		name = "Pepe";
		password = "123456";
		email = "mail@mail.com";
		location = "Oviedo";
		kind = "Person";
		kindCode = "1";

		Agent user = new Agent("11111111A", "123456", "Pepe", "mail@mail.com", "Oviedo", "Person", "1");

		assertTrue(user.getId().equals(id));
		assertTrue(user.getPassword().equals(password));
		assertTrue(user.getName().equals(name));
		assertTrue(user.getEmail().equals(email));
		assertTrue(user.getLocation().equals(location));
		assertTrue(user.getKindCode().equals(kindCode));
		assertTrue(user.getKind().equals(kind));

		Agent user2 = new Agent("11111111A", "123456", "Pepe", "mail@mail.com", "Oviedo", "Person", "1");

		user2.setPassword("asd");
		assertTrue(user2.getPassword().equals("asd"));
		user2.setName("Pedro");
		assertTrue(user2.getName().equals("Pedro"));
		user2.setEmail("pedro@gmail.com");
		assertTrue(user2.getEmail().equals("pedro@gmail.com"));
		user2.setLocation("Llanes");
		assertTrue(user2.getLocation().equals("Llanes"));
		user2.setKind("Officer");
		assertTrue(user2.getKind().equals("Officer"));

		assertTrue(user.equals(user2));
		assertTrue(user.hashCode()==user2.hashCode());
		

		assertTrue(user.toString().equals("UserInfo [ID=" + id + ", password=" + password + ", location=" + location
				+ ", kind=" + kind + ", kindCode=" + kindCode + ", name=" + name + ", email=" + email + "]"));
	}

}
