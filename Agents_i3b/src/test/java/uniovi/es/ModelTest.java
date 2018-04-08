package uniovi.es;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uniovi.es.entities.Agent;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ModelTest {

    @Test
    public void testUserInfo() throws Exception {
        String password;
        String name;
        String email;
        String location;
        String kind;
        String ID;
        String kindCode;
        
        name = "Pepe";
        password = "123456";
        email = "mail@mail.com";
        location = "Oviedo";
        kind = "Person";
        ID = "11111111A";
        kindCode="1";
        
        Agent user = new Agent("11111111A","123456","Pepe","mail@mail.com","Oviedo","Person","1");

        assertTrue(user.getPassword().equals(password));
        assertTrue(user.getName().equals(name));
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getLocation().equals(location));
        assertTrue(user.getId().equals(ID));
        assertTrue(user.getKindCode().equals(kindCode));
        assertTrue(user.getKind().equals(kind));
    }

}
