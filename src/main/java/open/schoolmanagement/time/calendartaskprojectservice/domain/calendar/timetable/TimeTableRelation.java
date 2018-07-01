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

/**
 * The type Time table relation.
 */
@Entity
@Table(name = "TIMETABLERELATION")
public class TimeTableRelation {


  @Id
  @GeneratedValue
  @Column(name = "timetablerelation_id")
  private Long timeTableRelationId;

  @OneToOne
  @JoinColumn(name = "timetableentry_id")
  private TimeTableEntry timeTableEntry;

  @OneToOne
  @JoinColumn(name = "timetable_id")
  private TimeTable timeTable;

  @OneToOne
  @JoinColumn(name = "classtimetable_id")
  private ClassTimeTable classTimeTable;

  @OneToOne
  @JoinColumn(name = "teachertimetable_id")
  private TeacherTimeTable teacherTimeTable;

  private TimeTableRelation(TimeTableRelationBuilder builder) {
    this.timeTableRelationId = builder.timeTableRelationId;
    this.timeTableEntry = builder.timeTableEntry;
    this.timeTable = builder.timeTable;
    this.classTimeTable = builder.classTimeTable;
    this.teacherTimeTable = builder.teacherTimeTable;
  }

  /**
   * Builder time table relation builder.
   *
   * @return the time table relation builder
   */
  public static TimeTableRelationBuilder builder() {
    return new TimeTableRelationBuilder();
  }

  /**
   * Gets time table relation id.
   *
   * @return the time table relation id
   */
  public Long getTimeTableRelationId() {
    return timeTableRelationId;
  }

  /**
   * Sets time table relation id.
   *
   * @param timeTableRelationId the time table relation id
   */
  public void setTimeTableRelationId(Long timeTableRelationId) {
    this.timeTableRelationId = timeTableRelationId;
  }

  /**
   * Gets time table entry.
   *
   * @return the time table entry
   */
  public TimeTableEntry getTimeTableEntry() {
    return timeTableEntry;
  }

  /**
   * Sets time table entry.
   *
   * @param timeTableEntry the time table entry
   */
  public void setTimeTableEntry(TimeTableEntry timeTableEntry) {
    this.timeTableEntry = timeTableEntry;
  }

  /**
   * Gets time table.
   *
   * @return the time table
   */
  public TimeTable getTimeTable() {
    return timeTable;
  }

  /**
   * Sets time table.
   *
   * @param timeTable the time table
   */
  public void setTimeTable(TimeTable timeTable) {
    this.timeTable = timeTable;
  }

  /**
   * Gets class time table.
   *
   * @return the class time table
   */
  public ClassTimeTable getClassTimeTable() {
    return classTimeTable;
  }

  /**
   * Sets class time table.
   *
   * @param classTimeTable the class time table
   */
  public void setClassTimeTable(ClassTimeTable classTimeTable) {
    this.classTimeTable = classTimeTable;
  }

  /**
   * Gets teacher time table.
   *
   * @return the teacher time table
   */
  public TeacherTimeTable getTeacherTimeTable() {
    return teacherTimeTable;
  }

  /**
   * Sets teacher time table.
   *
   * @param teacherTimeTable the teacher time table
   */
  public void setTeacherTimeTable(TeacherTimeTable teacherTimeTable) {
    this.teacherTimeTable = teacherTimeTable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TimeTableRelation)) {
      return false;
    }
    TimeTableRelation that = (TimeTableRelation) o;
    return Objects.equals(getTimeTableRelationId(), that.getTimeTableRelationId())
        && Objects.equals(getTimeTableEntry(), that.getTimeTableEntry())
        && Objects.equals(getTimeTable(), that.getTimeTable())
        && Objects.equals(getClassTimeTable(), that.getClassTimeTable())
        && Objects.equals(getTeacherTimeTable(), that.getTeacherTimeTable());
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        getTimeTableRelationId(),
        getTimeTableEntry(),
        getTimeTable(),
        getClassTimeTable(),
        getTeacherTimeTable());
  }

  /**
   * The type Time table relation builder.
   */
  public static final class TimeTableRelationBuilder {
    /**
     * The Time table relation builder.
     */
    TimeTableRelationBuilder timeTableRelationBuilder;
    private Long timeTableRelationId;
    private TimeTableEntry timeTableEntry;
    private TimeTable timeTable;
    private ClassTimeTable classTimeTable;
    private TeacherTimeTable teacherTimeTable;

    private TimeTableRelationBuilder() {
      this.timeTableRelationBuilder = new TimeTableRelationBuilder();
    }


    /**
     * Sets time table relation id.
     *
     * @param timeTableRelationId the time table relation id
     * @return the time table relation id
     */
    public TimeTableRelationBuilder setTimeTableRelationId(Long timeTableRelationId) {
      this.timeTableRelationId = timeTableRelationId;
      return this;
    }

    /**
     * Sets time table entry.
     *
     * @param timeTableEntry the time table entry
     * @return the time table entry
     */
    public TimeTableRelationBuilder setTimeTableEntry(TimeTableEntry timeTableEntry) {
      this.timeTableEntry = timeTableEntry;
      return this;
    }

    /**
     * Sets time table.
     *
     * @param timeTable the time table
     * @return the time table
     */
    public TimeTableRelationBuilder setTimeTable(TimeTable timeTable) {
      this.timeTable = timeTable;
      return this;
    }

    /**
     * Sets class time table.
     *
     * @param classTimeTable the class time table
     * @return the class time table
     */
    public TimeTableRelationBuilder setClassTimeTable(ClassTimeTable classTimeTable) {
      this.classTimeTable = classTimeTable;
      return this;
    }

    /**
     * Sets teacher time table.
     *
     * @param teacherTimeTable the teacher time table
     * @return the teacher time table
     */
    public TimeTableRelationBuilder setTeacherTimeTable(TeacherTimeTable teacherTimeTable) {
      this.teacherTimeTable = teacherTimeTable;
      return this;
    }

    /**
     * Build time table relation.
     *
     * @return the time table relation
     */
    public TimeTableRelation build() {
      return new TimeTableRelation(this);
    }
  }
}
