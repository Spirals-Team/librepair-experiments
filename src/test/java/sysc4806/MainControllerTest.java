package sysc4806;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by CraigBook on 2018-03-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void returnHello() throws Exception {
        String message = "Welcome to Project Management System";
        String body = this.testRestTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo(message);
    }

}