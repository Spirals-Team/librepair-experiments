package open.schoolmanagement.time.calendartaskprojectservice.service.calendar;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment.Appointment;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarService.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CalendarServiceTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();
  @Mock
  Person person1;
  @Mock
  Person person2;
  DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
  @Autowired
  private CalendarService calendarService;

  @Before
  public void setup() {

    initMocks(this);


    person1 = Person.builder()
        .setEmailAddress("hans@abc.de")
        .setFirstName("Hans")
        .setLastName("Meyer")
        .build();

    person2 = Person.builder()
        .setEmailAddress("christa@abc.de")
        .setFirstName("Christa")
        .setLastName("Meyer")
        .build();

  }


  @Test
  public void  test_createAppointment() throws ParseException {

    String startString = "23-Jun-2018, 19:00:00 PM";
    String endString = "23-Jun-2018, 23:30:00 PM";

    Date start = formatter.parse(startString);
    Date end = formatter.parse(endString);

    String subject = "A Perfect Circle in Luxemburg";
    String description = "Get your ultimate Fan experience! A PERFECT CIRCLE VIP PACKAGE includes:\n" +
        "\n" +
        "One general admission ticket \n" +
        "VIP early entry into the venue (get in first and close to the stage!) \n" +
        "Specially designed A Perfect Circle tour shirt (exclusive to VIP package only!) \n" +
        "Collectible tour poster autographed by the band (limited print) \n" +
        "APC tote bag \n" +
        "Exclusive A Perfect Circle VIP merchandise item \n" +
        "Commemorative tour laminate \n" +
        "Commemorative ticket Crowd-free merchandise shopping ";

    Appointment appointment = Appointment.builder()
        .setStart(start)
        .setEnd(end)
        .setAppointmentSubject(subject)
        .setDescription(description)
        .setOwner(person1)
        .build();

    Appointment createdAppointment = calendarService.createAppointment(appointment.getStart(),
        appointment
            .getEnd(),
        appointment
            .getAppointmentSubject(), appointment.getDescription(), appointment.getOwner());

    assertThat(createdAppointment.getAppointmentId() != null).isTrue();
    assertThat(createdAppointment.getAppointmentSubject()).isEqualTo(appointment.getAppointmentSubject());
    assertThat(createdAppointment.getDescription()).isEqualTo(appointment.getDescription());
    assertThat(createdAppointment.getOwner()).isEqualTo(appointment.getOwner());
    assertThat(createdAppointment.getStart()).isEqualTo(appointment.getStart());
    assertThat(createdAppointment.getEnd()).isEqualTo(appointment.getEnd());
  }


}