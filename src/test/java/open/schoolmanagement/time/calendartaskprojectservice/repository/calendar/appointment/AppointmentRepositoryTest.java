package open.schoolmanagement.time.calendartaskprojectservice.repository.calendar.appointment;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment.Appointment;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = AppointmentRepository.class)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AppointmentRepositoryTest {


  @Autowired
  @Qualifier("appointmentRepository")
  AppointmentRepository appointmentRepository;


  Appointment appointment1;
  Appointment appointment2;
  Appointment appointment3;
  Appointment appointment4;

  Person person1;
  Person person2;


  @Before
  public void setUp() throws Exception {
    initMocks(this);


  }

  @After
  public void tearDown() {
    person1 = null;
    person2 = null;

  }

  @Test
  public void findBySubjectAndOwner() {

  }

  @Test
  public void findByStartAndOwner() {
  }

  @Test
  public void findByOwner() {

    person1 = Person.builder().setFirstName("Peter").setEmailAddress("Parker").setEmailAddress
        ("peter@spiderman.com").build();
    person1.setPersonId(12334567L);

    person2 = Person.builder().setFirstName("Mary").setEmailAddress("Watson").setEmailAddress
        ("mary@spiderman.com").build();
    person2.setPersonId(1234561234L);

    Calendar calendar = Calendar.getInstance();
    calendar.set(2018, 3, 23, 9, 0);
    Date startDate = calendar.getTime();

    calendar.set(2018, 3, 23, 9, 30);
    Date endDate = calendar.getTime();

    String description = "Doctor Octopus (Otto Octavius) is a fictional character appearing in "
        + "American comic books published by Marvel Comics. A highly intelligent mad scientist, "
        + "Doctor Octopus is typically portrayed as a stocky, myopic man who utilizes four powerful,"
        + " mechanical appendages, and is obsessed with proving his own genius and destroying the "
        + "superhero Spider-Man.";

    Long duration = endDate.getTime() - startDate.getTime();


    appointment1 = Appointment.builder().setAppointmentSubject("Find Octopus").setOwner(person1)
        .setStart(startDate).setEnd(endDate).setDuration(duration).setDescription(description)
        .setLocation("New York").build();

    appointmentRepository.save(appointment1);

    calendar.set(2018, 3, 25, 10, 0);
    startDate = calendar.getTime();

    calendar.set(2018, 3, 25, 11, 30);
    endDate = calendar.getTime();


    description = "Lady Octopus (real name Carolyn Trainer and also known as Doctor Octopus II) " +
        "is a supervillainess in Marvel Comics. The character is depicted as a protégée to her " +
        "predecessor of the name and primarily an enemy to Spider-Man and Scarlet Spider (Ben Reilly).";

    duration = endDate.getTime() - startDate.getTime();


    appointment2 = Appointment.builder().setAppointmentSubject("Find Octopus II").setOwner(person1)
        .setStart(startDate).setEnd(endDate).setDuration(duration).setDescription(description)
        .setLocation("New York").build();

    appointmentRepository.save(appointment2);


    Collection<Appointment> result = appointmentRepository.findByOwner(person1.getPersonId());

  }


  @Test
  public void findByTermAndOwner() {
  }

  @Test
  public void findByDayAndOwner() {
  }

  @Test
  public void findByDateRangeAndOwner() {
  }


}