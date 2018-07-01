package open.schoolmanagement.time.calendartaskprojectservice;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class CalendarTaskProjectServiceApplicationTests {


  @Before
  public void beforeTestCase() {
    initMocks(this);
  }


  @Test
  public void contextLoads() {

  }

}
