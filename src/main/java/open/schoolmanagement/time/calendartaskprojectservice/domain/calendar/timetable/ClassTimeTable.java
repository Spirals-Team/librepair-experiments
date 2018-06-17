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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.classes.SchoolClass;


/**
 * The type Class time table.
 */
@Entity
@Table(name = "classtimetable")
public class ClassTimeTable {

  @Id
  @GeneratedValue
  @Column(name = "classtimetable_id")
  private Long classTimeTableId;

  @OneToOne(mappedBy = "schoolclass")
  @JoinColumn(name = "schoolclass_id")
  private SchoolClass schoolClass;

  @OneToMany(mappedBy = "timetableentry", fetch = FetchType.LAZY)
  private Collection<TimeTableEntry> timeTableEntries;

  /**
   * Instantiates a new Class time table.
   *
   * @param builder the builder
   */
  private ClassTimeTable(ClassTimeTableBuilder builder) {
    this.schoolClass = builder.schoolClass;
    this.timeTableEntries = builder.timeTableEntries;
  }


  /**
   * Gets class time table id.
   *
   * @return the class time table id
   */
  public Long getClassTimeTableId() {
    return classTimeTableId;
  }

  /**
   * Sets class time table id.
   *
   * @param classTimeTableId the class time table id
   */
  public void setClassTimeTableId(Long classTimeTableId) {
    this.classTimeTableId = classTimeTableId;
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
   * Gets time table entries.
   *
   * @return the time table entries
   */
  public Collection<TimeTableEntry> getTimeTableEntries() {
    return timeTableEntries;
  }

  /**
   * Sets time table entries.
   *
   * @param timeTableEntries the time table entries
   */
  public void setTimeTableEntries(Collection<TimeTableEntry> timeTableEntries) {
    this.timeTableEntries = timeTableEntries;
  }


  /**
   * Add time table entry.
   *
   * @param timeTableEntry the time table entry
   */
  public void addTimeTableEntry(TimeTableEntry timeTableEntry) {
    this.timeTableEntries.add(timeTableEntry);
  }

  /**
   * Remove time table entry.
   *
   * @param timeTableEntry the time table entry
   */
  public void removeTimeTableEntry(TimeTableEntry timeTableEntry) {
    this.timeTableEntries.remove(timeTableEntry);
  }

  /**
   * Remove time table entry.
   *
   * @param timeTableEntryId the time table entry id
   */
  public void removeTimeTableEntry(Long timeTableEntryId) {
    this.timeTableEntries.stream().filter(timeTableEntry -> Objects.equals(timeTableEntry
        .getTimeTableEntryId(), timeTableEntryId)).findFirst()
        .ifPresent(this::removeTimeTableEntry);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClassTimeTable)) {
      return false;
    }
    ClassTimeTable that = (ClassTimeTable) o;
    return Objects.equals(getClassTimeTableId(), that.getClassTimeTableId())
        && Objects.equals(getSchoolClass(), that.getSchoolClass())
        && Objects.equals(getTimeTableEntries(), that.getTimeTableEntries());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getClassTimeTableId(), getSchoolClass(), getTimeTableEntries());
  }


  /**
   * The type Class time table builder.
   */
  public static final class ClassTimeTableBuilder {

    private SchoolClass schoolClass;
    private Collection<TimeTableEntry> timeTableEntries;

    /**
     * Builder class time table builder.
     *
     * @return the class time table builder
     */
    public static ClassTimeTableBuilder builder() {
      return new ClassTimeTableBuilder();
    }

    /**
     * Sets school class.
     *
     * @param schoolClass the school class
     * @return the school class
     */
    public ClassTimeTableBuilder setSchoolClass(SchoolClass schoolClass) {
      this.schoolClass = schoolClass;
      return this;
    }

    /**
     * Sets time table entries.
     *
     * @param timeTableEntries the time table entries
     * @return the time table entries
     */
    public ClassTimeTableBuilder setTimeTableEntries(Collection<TimeTableEntry> timeTableEntries) {
      this.timeTableEntries = timeTableEntries;
      return this;
    }

    /**
     * Build class time table.
     *
     * @return the class time table
     */
    public ClassTimeTable build() {
      return new ClassTimeTable(this);
    }
  }
}
