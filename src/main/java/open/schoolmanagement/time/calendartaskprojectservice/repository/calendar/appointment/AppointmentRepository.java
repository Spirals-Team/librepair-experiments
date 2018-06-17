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

package open.schoolmanagement.time.calendartaskprojectservice.repository.calendar.appointment;

import java.util.Collection;
import java.util.Date;
import open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * The interface Appointment repository.
 */
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

  /**
   * Find appointment by subject and for the owning person.
   *
   * @param subject subject of the appointment.
   * @param person  Owner of the appointment.
   * @return Appointments collection
   */
  @Query("SELECT a FROM Appointment a where a.subject LIKE %?1 and where a.owner EQUALS %?2")
  Collection<Appointment> findBySubject(String subject, Long person);


  /**
   * Find appointment by Start Time and for the owning person.
   *
   * @param start    start Date of the appointment
   * @param personId owner id of the appointment
   * @return Appointments collection
   */
  @Query("SELECT a FROM Appointment a where a.start LIKE %?1 and where a.owner EQUALS %?2")
  Collection<Appointment> findByStart(Date start, Long personId);


  /**
   * Find by for whom collection.
   *
   * @param personId the person id
   * @return the collection
   */
  @Query("SELECT a FROM Appointment a where a.owner EQUALS %?1")
  Collection<Appointment> findByForWhom(Long personId);
}
