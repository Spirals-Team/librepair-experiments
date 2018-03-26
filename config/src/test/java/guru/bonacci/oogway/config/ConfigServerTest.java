package guru.bonacci.oogway.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigTestApp.class, properties = {
	"spring.profiles.active=native"
})
public class ConfigServerTest {

//    @Autowired
//    private EnvironmentController controller;
//
//    @Test
//    public void contextLoads() {
//        assertThat(controller).isNotNull();
//    }

}