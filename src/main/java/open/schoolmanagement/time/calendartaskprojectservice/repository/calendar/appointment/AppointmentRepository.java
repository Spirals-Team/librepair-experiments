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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The interface Appointment repository.
 */
@Repository("appointmentRepository")
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  /**
   * Find appointment by subject and for the owning person.
   *
   * @param subject subject of the appointment.
   * @param person  Owner of the appointment.
   * @return Appointments collection
   */
  @Query("SELECT a FROM Appointment a where a.appointmentSubject LIKE :subject "
      + "AND a.owner = :owner")
  Collection<Appointment> findBySubjectAndOwner(@Param("subject") String subject, @Param("owner")
      Long person);


  @Query("SELECT a FROM Appointment a where a.appointmentSubject LIKE :term "
      + "or a.description LIKE"
      + " term AND a.owner = :owner")
  Collection<Appointment> findByTermAndOwner(@Param("term") String term, @Param("owner")
      Long person);

  /**
   * Find appointment by Start Time and for the owning person.
   *
   * @param pit    start Date of the appointment
   * @param person owner id of the appointment
   * @return Appointments collection
   */
  @Query("SELECT a FROM Appointment a where ( a.start <= :pit and a.end >= :pit ) and a.owner = "
      + ":person")
  Collection<Appointment> findByDayAndOwner(@Param("pit") Date pit, @Param("person") Long
      person);


  @Query("SELECT a FROM Appointment a where ( a.start <= :begin_range and a.end >= :end_range ) "
      + "and a.owner = :person")
  Collection<Appointment> findByDateRangeAndOwner(@Param("begin_range") Date begin, @Param
      ("end_range") Date end, @Param("person") Long person);


  /**
   * Find by for whom collection.
   *
   * @param person the person id
   * @return the collection
   */
  @Query("SELECT a FROM Appointment a where a.owner = :person")
  Collection<Appointment> findByOwner(@Param("person") Long person);
}
