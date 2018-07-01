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

  @OneToOne
  @JoinColumn(name = "schoolclass_id")
  private SchoolClass schoolClass;

  @OneToMany
  @JoinColumn(name = "classtimetable_id")
  private Collection<TimeTableRelation> timeTableRelations;

  private ClassTimeTable(ClassTimeTableBuilder builder) {
    this.schoolClass = builder.schoolClass;
    this.timeTableRelations = builder.timeTableRelations;
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
   * Gets time table relations.
   *
   * @return the time table relations
   */
  public Collection<TimeTableRelation> getTimeTableRelations() {
    return timeTableRelations;
  }

  /**
   * Sets time table entries.
   *
   * @param timeTableRelations the time table relations
   */
  public void setTimeTableEntries(Collection<TimeTableRelation> timeTableRelations) {
    this.timeTableRelations = timeTableRelations;
  }


  /**
   * Add time table relation.
   *
   * @param timeTableRelation the time table relation
   */
  public void addTimeTableRelation(TimeTableRelation timeTableRelation) {
    this.timeTableRelations.add(timeTableRelation);
  }

  /**
   * Remove time table relation.
   *
   * @param timeTableRelation the time table relation
   */
  public void removeTimeTableRelation(TimeTableRelation timeTableRelation) {
    this.timeTableRelations.remove(timeTableRelation);
  }

  /**
   * Remove time table entry.
   *
   * @param timeTableRelationId the time table relation id
   */
  public void removeTimeTableEntry(Long timeTableRelationId) {
    this.timeTableRelations.stream().filter(timeTableRelation -> Objects.equals(timeTableRelation
        .getTimeTableRelationId(), timeTableRelationId)).findFirst()
        .ifPresent(this::removeTimeTableRelation);
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
        && Objects.equals(getTimeTableRelations(), that.getTimeTableRelations());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getClassTimeTableId(), getSchoolClass(), getTimeTableRelations());
  }


  /**
   * The type Class time table builder.
   */
  public static final class ClassTimeTableBuilder {

    private SchoolClass schoolClass;
    private Collection<TimeTableRelation> timeTableRelations;

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
     * @param timeTableRelations the time table relations
     * @return the time table entries
     */
    public ClassTimeTableBuilder setTimeTableEntries(Collection<TimeTableRelation>
                                                         timeTableRelations) {
      this.timeTableRelations = timeTableRelations;
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
