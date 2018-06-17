package open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment;

import static org.assertj.core.api.Assertions.assertThat;

import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The type Appointment test.
 */
@RunWith(SpringRunner.class)
class AppointmentTest {



  /**
   * Equals of the same appointment is true.
   */
  @Test
  public void equals_of_the_same_appointment_is_true() {

    Appointment appointment1 = Appointment.builder()
        .setAppointmentSubject("Appointment Subject")
        .build();
    Appointment appointment2 = Appointment.builder()
        .setAppointmentSubject("Appointment Subject")
        .build();

    assertThat(appointment1).isEqualTo(appointment2);
  }

  /**
   * Equals of the appointment is false.
   */
  @Test
  public void equals_of_the_appointment_is_false() {


    Appointment appointment1 = Appointment.builder()
        .setAppointmentSubject("Appointment Subject1 ")
        .build();
    Appointment appointment2 = Appointment.builder()
        .setAppointmentSubject("Appointment Subject2")
        .build();

    assertThat(appointment1).isNotEqualTo(appointment2);


    Person person = Person.builder().setFirstName("Peter").setLastName("Parker").build();

    appointment1 = Appointment.builder()
        .setAppointmentSubject("Appointment Subject")
        .build();
    appointment2 = Appointment.builder()
        .setAppointmentSubject("Appointment Subject")
        .setOwner(person)
        .build();

    assertThat(appointment1).isNotEqualTo(appointment2);
  }


  /**
   * Equals of with other object is false.
   */
  @Test
  public void equals_of_with_other_object_is_false() {
    Appointment appointment = Appointment.builder().setAppointmentSubject("Test").build();

    String test = "Test";

    assertThat(appointment).isNotEqualTo(test);
  }





}