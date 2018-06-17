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

package open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.appointment;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;

/**
 * The type Appointment.
 */
@Table(name = "appointment")
@Entity
public class Appointment {

  @Id
  @GeneratedValue
  @Column(name = "appointment_id")
  private Long appointmentId;

  @Column(name = "appointment_start")
  private Date start;

  @Column(name = "appointment_end")
  private Date end;

  @Column(name = "appointment_duration")
  private Long duration;

  @Column(name = "appointment_subject")
  private String appointmentSubject;

  @Column(name = "description")
  private String description;

  @Column(name = "location")
  private String location;

  @OneToOne(mappedBy = "appointment_owner")
  private Person owner;

  /**
   * Instantiates a new Appointment.
   *
   * @param builder the builder
   */
  private Appointment(AppointmentBuilder builder) {
    this.start = builder.start;
    this.end = builder.end;
    this.duration = builder.duration;
    this.appointmentSubject = builder.appointmentSubject;
    this.description = builder.description;
    this.location = builder.location;
    this.owner = builder.owner;
  }

  /**
   * Builder appointment builder.
   *
   * @return the appointment builder
   */
  public static AppointmentBuilder builder() {
    return new AppointmentBuilder();
  }

  /**
   * Gets appointment id.
   *
   * @return the appointment id
   */
  public Long getAppointmentId() {
    return appointmentId;
  }

  /**
   * Sets appointment id.
   *
   * @param appointmentId the appointment id
   */
  public void setAppointmentId(Long appointmentId) {
    this.appointmentId = appointmentId;
  }

  /**
   * Gets start.
   *
   * @return the start
   */
  public Date getStart() {
    return start;
  }

  /**
   * Sets start.
   *
   * @param start the start
   */
  public void setStart(Date start) {
    this.start = start;
  }

  /**
   * Gets end.
   *
   * @return the end
   */
  public Date getEnd() {
    return end;
  }

  /**
   * Sets end.
   *
   * @param end the end
   */
  public void setEnd(Date end) {
    this.end = end;
  }

  /**
   * Gets duration.
   *
   * @return the duration
   */
  public Long getDuration() {
    return duration;
  }

  /**
   * Sets duration.
   *
   * @param duration the duration
   */
  public void setDuration(Long duration) {
    this.duration = duration;
  }

  /**
   * Gets appointment subject.
   *
   * @return the appointment subject
   */
  public String getAppointmentSubject() {
    return appointmentSubject;
  }

  /**
   * Sets appointment subject.
   *
   * @param appointmentSubject the appointment subject
   */
  public void setAppointmentSubject(String appointmentSubject) {
    this.appointmentSubject = appointmentSubject;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets location.
   *
   * @param location the location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Gets owner.
   *
   * @return the owner
   */
  public Person getOwner() {
    return owner;
  }

  /**
   * Sets owner.
   *
   * @param owner the owner
   */
  public void setOwner(Person owner) {
    this.owner = owner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Appointment)) {
      return false;
    }
    Appointment that = (Appointment) o;
    return Objects.equals(getAppointmentId(), that.getAppointmentId())
        && Objects.equals(getStart(), that.getStart())
        && Objects.equals(getEnd(), that.getEnd())
        && Objects.equals(getAppointmentSubject(), that.getAppointmentSubject())
        && Objects.equals(getOwner(), that.getOwner());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getAppointmentId(),
        getStart(),
        getEnd(),
        getAppointmentSubject(),
        getOwner());
  }

  /**
   * The type Appointment builder.
   */
  public static final class AppointmentBuilder {
    private Date start;
    private Date end;
    private Long duration;
    private String appointmentSubject;
    private String description;
    private String location;
    private Person owner;


    private AppointmentBuilder() {

    }

    public static final AppointmentBuilder builder() {
      return new AppointmentBuilder();
    }


    /**
     * Sets start.
     *
     * @param start the start
     * @return the start
     */
    public AppointmentBuilder setStart(Date start) {
      this.start = start;
      return this;
    }

    /**
     * Sets end.
     *
     * @param end the end
     * @return the end
     */
    public AppointmentBuilder setEnd(Date end) {
      this.end = end;
      return this;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     * @return the duration
     */
    public AppointmentBuilder setDuration(Long duration) {
      this.duration = duration;
      return this;
    }

    /**
     * Sets appointment subject.
     *
     * @param appointmentSubject the appointment subject
     * @return the appointment subject
     */
    public AppointmentBuilder setAppointmentSubject(String appointmentSubject) {
      this.appointmentSubject = appointmentSubject;
      return this;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public AppointmentBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets location.
     *
     * @param location the location
     * @return the location
     */
    public AppointmentBuilder setLocation(String location) {
      this.location = location;
      return this;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     * @return the owner
     */
    public AppointmentBuilder setOwner(Person owner) {
      this.owner = owner;
      return this;
    }

    /**
     * Build appointment.
     *
     * @return the appointment
     */
    public Appointment build() {
      return new Appointment(this);
    }
  }
}
