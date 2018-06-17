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

package open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.timetable;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.classes.SchoolClass;
import open.schoolmanagement.time.calendartaskprojectservice.domain.classes.SchoolSubject;
import open.schoolmanagement.time.calendartaskprojectservice.domain.classes.Teacher;

/**
 * The type Time table entry.
 */
@Entity
@Table(name = "timetableentry")
public class TimeTableEntry {

  @Id
  @GeneratedValue
  @Column(name = "timetableentry_id")
  private Long timeTableEntryId;

  @Column(name = "course")
  private String course;

  @OneToOne(mappedBy = "schoolClass")
  @JoinColumn(name = "schoolclass_id")
  private SchoolClass schoolClass;

  @OneToOne(mappedBy = "timetableentry_subject")
  private SchoolSubject timetableentrySubject;

  @OneToOne(mappedBy = "teacher")
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @Column(name = "room")
  private String room;

  @Column(name = "weekday")
  private WeekDay weekDay;

  @Column(name = "schoolhour")
  private SchoolHour schoolHour;

  private TimeTableEntry(TimeTableEntryBuilder builder) {

    this.course = builder.course;
    this.schoolClass = builder.schoolClass;
    this.timetableentrySubject = builder.timetableentrySubject;
    this.teacher = builder.teacher;
    this.room = builder.room;
    this.weekDay = builder.weekDay;
    this.schoolHour = builder.schoolHour;
  }

  /**
   * Gets time table entry id.
   *
   * @return the time table entry id
   */
  public Long getTimeTableEntryId() {
    return timeTableEntryId;
  }

  /**
   * Sets time table entry id.
   *
   * @param timeTableEntryId the time table entry id
   */
  public void setTimeTableEntryId(Long timeTableEntryId) {
    this.timeTableEntryId = timeTableEntryId;
  }

  /**
   * Gets course.
   *
   * @return the course
   */
  public String getCourse() {
    return course;
  }

  /**
   * Sets course.
   *
   * @param course the course
   */
  public void setCourse(String course) {
    this.course = course;
  }

  /**
   * Gets school class.
   *
   * @return the school class
   */
  public SchoolClass getSchoolClass() {
    return schoolClass;
  }

  /**
   * Sets school class.
   *
   * @param schoolClass the school class
   */
  public void setSchoolClass(SchoolClass schoolClass) {
    this.schoolClass = schoolClass;
  }

  /**
   * Gets timetableentry subject.
   *
   * @return the timetableentry subject
   */
  public SchoolSubject getTimetableentrySubject() {
    return timetableentrySubject;
  }

  /**
   * Sets timetableentry subject.
   *
   * @param timetableentrySubject the timetableentry subject
   */
  public void setTimetableentrySubject(SchoolSubject timetableentrySubject) {
    this.timetableentrySubject = timetableentrySubject;
  }

  /**
   * Gets teacher.
   *
   * @return the teacher
   */
  public Teacher getTeacher() {
    return teacher;
  }

  /**
   * Sets teacher.
   *
   * @param teacher the teacher
   */
  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  /**
   * Gets room.
   *
   * @return the room
   */
  public String getRoom() {
    return room;
  }

  /**
   * Sets room.
   *
   * @param room the room
   */
  public void setRoom(String room) {
    this.room = room;
  }

  /**
   * Gets week day.
   *
   * @return the week day
   */
  public WeekDay getWeekDay() {
    return weekDay;
  }

  /**
   * Sets week day.
   *
   * @param weekDay the week day
   */
  public void setWeekDay(WeekDay weekDay) {
    this.weekDay = weekDay;
  }

  /**
   * Gets school hour.
   *
   * @return the school hour
   */
  public SchoolHour getSchoolHour() {
    return schoolHour;
  }

  /**
   * Sets school hour.
   *
   * @param schoolHour the school hour
   */
  public void setSchoolHour(SchoolHour schoolHour) {
    this.schoolHour = schoolHour;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TimeTableEntry)) {
      return false;
    }
    TimeTableEntry that = (TimeTableEntry) o;
    return Objects.equals(getTimeTableEntryId(), that.getTimeTableEntryId())
        && Objects.equals(getCourse(), that.getCourse())
        && Objects.equals(getSchoolClass(), that.getSchoolClass())
        && Objects.equals(getTimetableentrySubject(), that.getTimetableentrySubject())
        && getWeekDay() == that.getWeekDay()
        && getSchoolHour() == that.getSchoolHour();
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        getTimeTableEntryId(),
        getCourse(),
        getSchoolClass(),
        getTimetableentrySubject(),
        getWeekDay(),
        getSchoolHour());
  }


  /**
   * The type Time table entry builder.
   */
  public static final class TimeTableEntryBuilder {
    private Long timeTableEntryId;
    private String course;
    private SchoolClass schoolClass;
    private SchoolSubject timetableentrySubject;
    private Teacher teacher;
    private String room;
    private WeekDay weekDay;
    private SchoolHour schoolHour;

    /**
     * Sets time table entry id.
     *
     * @param timeTableEntryId the time table entry id
     * @return the time table entry id
     */
    public TimeTableEntryBuilder setTimeTableEntryId(Long timeTableEntryId) {
      this.timeTableEntryId = timeTableEntryId;
      return this;
    }

    /**
     * Sets course.
     *
     * @param course the course
     * @return the course
     */
    public TimeTableEntryBuilder setCourse(String course) {
      this.course = course;
      return this;
    }

    /**
     * Sets school class.
     *
     * @param schoolClass the school class
     * @return the school class
     */
    public TimeTableEntryBuilder setSchoolClass(SchoolClass schoolClass) {
      this.schoolClass = schoolClass;
      return this;
    }

    /**
     * Sets timetableentry subject.
     *
     * @param timetableentrySubject the timetableentry subject
     * @return the timetableentry subject
     */
    public TimeTableEntryBuilder setTimetableentrySubject(SchoolSubject timetableentrySubject) {
      this.timetableentrySubject = timetableentrySubject;
      return this;
    }

    /**
     * Sets teacher.
     *
     * @param teacher the teacher
     * @return the teacher
     */
    public TimeTableEntryBuilder setTeacher(Teacher teacher) {
      this.teacher = teacher;
      return this;
    }

    /**
     * Sets room.
     *
     * @param room the room
     * @return the room
     */
    public TimeTableEntryBuilder setRoom(String room) {
      this.room = room;
      return this;
    }

    /**
     * Sets week day.
     *
     * @param weekDay the week day
     * @return the week day
     */
    public TimeTableEntryBuilder setWeekDay(WeekDay weekDay) {
      this.weekDay = weekDay;
      return this;
    }

    /**
     * Sets school hour.
     *
     * @param schoolHour the school hour
     * @return the school hour
     */
    public TimeTableEntryBuilder setSchoolHour(SchoolHour schoolHour) {
      this.schoolHour = schoolHour;
      return this;
    }

    /**
     * Build time table entry.
     *
     * @return the time table entry
     */
    public TimeTableEntry build() {
      return new TimeTableEntry(this);
    }
  }
}
