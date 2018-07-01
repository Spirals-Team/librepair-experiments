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

import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.classes.Teacher;

/**
 * The type Teacher time table.
 */
@Table(name = "teachertimetable")
@Entity
public class TeacherTimeTable {

  @Id
  @GeneratedValue
  @Column(name = "teachertimetable_id")
  private Long teacherTimetableId;

  @OneToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @OneToMany
  @JoinColumn(name = "teachertimetable_id")
  private Collection<TimeTableRelation> timeTableRelations;


  private TeacherTimeTable(TeacherTimeTableBuilder builder) {
    this.teacher = builder.teacher;
    this.timeTableRelations = builder.timeTableRelations;
  }

  public static TeacherTimeTableBuilder builder() {
    return new TeacherTimeTableBuilder();
  }

  /**
   * Gets teacher timetable id.
   *
   * @return the teacher timetable id
   */
  public Long getTeacherTimetableId() {
    return teacherTimetableId;
  }

  /**
   * Sets teacher timetable id.
   *
   * @param teacherTimetableId the teacher timetable id
   */
  public void setTeacherTimetableId(Long teacherTimetableId) {
    this.teacherTimetableId = teacherTimetableId;
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
   * Gets time table entries.
   *
   * @return the time table entries
   */
  public Collection<TimeTableRelation> getTimeTableRelations() {
    return timeTableRelations;
  }

  /**
   * Sets time table entries.
   *
   * @param timeTableRelations the time table entries
   */
  public void setTimeTableRelations(Collection<TimeTableRelation> timeTableRelations) {
    this.timeTableRelations = timeTableRelations;
  }

  /**
   * Add time table entries.
   *
   * @param timeTableRelation the time table entry
   */
  public void addTimeTableRelations(TimeTableRelation timeTableRelation) {
    this.timeTableRelations.add(timeTableRelation);
  }

  /**
   * Remove time table entry.
   *
   * @param timeTableRelationId the time table entry id
   */
  public void removeTimeTableRelation(Long timeTableRelationId) {
    this.timeTableRelations.stream().filter(timeTableEntry -> Objects.equals(timeTableEntry
        .getTimeTableRelationId(), timeTableRelationId))
        .findFirst()
        .ifPresent(this::removeTimeTableRelation);
  }

  /**
   * Remove time table entry.
   *
   * @param timeTableRelation the time table entry
   */
  public void removeTimeTableRelation(TimeTableRelation timeTableRelation) {
    this.timeTableRelations.remove(timeTableRelation);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeacherTimeTable)) {
      return false;
    }
    TeacherTimeTable that = (TeacherTimeTable) o;
    return Objects.equals(getTeacherTimetableId(), that.getTeacherTimetableId())
        && Objects.equals(getTeacher(), that.getTeacher());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getTeacherTimetableId(), getTeacher());
  }

  /**
   * The type Teacher time table builder.
   */
  public static final class TeacherTimeTableBuilder {
    private Long teacherTimetableId;
    private Teacher teacher;
    private Collection<TimeTableRelation> timeTableRelations;


    private TeacherTimeTableBuilder() {

    }


    /**
     * Sets teacher timetable id.
     *
     * @param teacherTimetableId the teacher timetable id
     * @return the teacher timetable id
     */
    public TeacherTimeTableBuilder setTeacherTimetableId(Long teacherTimetableId) {
      this.teacherTimetableId = teacherTimetableId;
      return this;
    }

    /**
     * Sets teacher.
     *
     * @param teacher the teacher
     * @return the teacher
     */
    public TeacherTimeTableBuilder setTeacher(Teacher teacher) {
      this.teacher = teacher;
      return this;
    }

    /**
     * Sets time table entries.
     *
     * @param timeTableRelations the time table entries
     * @return the time table entries
     */
    public TeacherTimeTableBuilder setTimeTableRelations(
        Collection<TimeTableRelation> timeTableRelations) {
      this.timeTableRelations = timeTableRelations;
      return this;
    }

    /**
     * Build teacher time table.
     *
     * @return the teacher time table
     */
    public TeacherTimeTable build() {
      return new TeacherTimeTable(this);
    }
  }
}
