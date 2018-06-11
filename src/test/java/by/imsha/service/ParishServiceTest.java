package by.imsha.service;

import by.imsha.Application;
import by.imsha.domain.City;
import by.imsha.domain.Coordinate;
import by.imsha.domain.Parish;
import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

/**
 * @author Alena Misan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({"server.port=0"})
public class ParishServiceTest {

    @Autowired
    private ParishService service;

    @Value("${local.server.port}")
    private int port;

    List<Parish> parishes;

    @Before
    public void setUp(){
//        parishes.add(p1);
    }

    @Test
    public void shouldCreateParish(){
      // TODO add correct config for DB
//        Parish p1 = new Parish("001", "Andrei", new Coordinate(11.1f, 22.2f), new City("Minsk"), "80992002", "ana@ana.ass" );
//        Parish p2 = service.createParish(p1);
//        assertThat(p1, is(p2));
    }

}
