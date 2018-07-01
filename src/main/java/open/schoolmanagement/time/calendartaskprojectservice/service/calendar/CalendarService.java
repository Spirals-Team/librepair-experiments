/*
   Copyright 2018 Open School Management
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package open.schoolmanagement.time.calendartaskprojectservice.service.calendar;

import java.util.Collection;
import java.util.Date;
import open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment.Appointment;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;
import open.schoolmanagement.time.calendartaskprojectservice.repository.calendar.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {


  @Autowired
  @Qualifier("appointmentRepository")
  private AppointmentRepository appointmentRepository;

  /**
   * Create an appointment.
   *
   * @param start       Start of an Appointment.
   * @param end         End of an Appointment.
   * @param subject     Subject of an Appointment.
   * @param description Description of ein Appointment.
   * @return a new Appointment
   **/
  public Appointment createAppointment(Date start, Date end, String subject, String description,
                                       Person owner) {


    long duration = end.getTime() - start.getTime();

    Appointment appointment = Appointment.builder()
        .setStart(start).setEnd(end)
        .setDuration(duration)
        .setAppointmentSubject(subject)
        .setDescription(description)
        .setOwner(owner)
        .build();


    appointment = appointmentRepository.save(appointment);

    return appointment;
  }

  /**
   * Return all appointment for the current user.
   *
   * @return Collection of Appointments
   */
  public Collection<Appointment> listAppointmentsForUser(Person person) {

    if (person != null) {
      return appointmentRepository.findByOwner(person.getPersonId());
    } else {
      return null;
    }
  }

  /**
   * Return all appointments for a certain date and a person.
   *
   * @param date   Day of the Appointement
   * @param person Owner of the Appointments
   * @return Collection of Appointments
   */
  public Collection<Appointment> listAppointementByDate(Date date, Person person) {

    if (date != null && person != null) {
      return appointmentRepository.findByDayAndOwner(date, person.getPersonId());
    } else {
      return null;
    }

  }
}
