package open.schoolmanagement.time.calendartaskprojectservice.controller.calendar;

import open.schoolmanagement.time.calendartaskprojectservice.service.calendar.CalendarService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = CalendarController.class)
class CalendarControllerTest {


  @Autowired
  private CalendarController calendarController;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private CalendarService calendarService;

  @Autowired
  private MockMvc mockMvc;

  @Before
  void setUp() {
  }

  @After
  void tearDown() {
  }

  @Test
  void addAppointment() {

  }

  @Test
  void listAppointments() {


  }


}