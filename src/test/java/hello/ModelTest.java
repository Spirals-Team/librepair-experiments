package hello;

import static org.junit.Assert.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import uniovi.es.Application;
import uniovi.es.entities.Agent;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("repository")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ModelTest {
    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        //noinspection deprecation
        template = new TestRestTemplate();
    }

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
        //UserInfo2 user = new UserInfo2("11111111A","123456","Pepe","mail@mail.com","Oviedo","Person");

        assertTrue(user.getPassword().equals(password));
        assertTrue(user.getName().equals(name));
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getLocation().equals(location));
        assertTrue(user.getId().equals(ID));
        assertTrue(user.getKindCode().equals(kindCode));
    }

}
