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
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The type Time table.
 */
@Entity
@Table(name = "timetable")
public class TimeTable {

  @Id
  @GeneratedValue
  @Column(name = "timetable_id")
  private Long timetableId;

  @Column(name = "timetable_name")
  private String timetableName;

  @Column(name = "validfrom")
  private Date validFrom;

  @Column(name = "validto")
  private Date validTo;

  @OneToMany
  @JoinColumn(name = "timetable_id")
  private Collection<TimeTableRelation> timeTableRelations;

  private TimeTable(TimeTableBuilder builder) {
    this.timetableId = builder.timetableId;
    this.timetableName = builder.timetableName;
    this.validFrom = builder.validFrom;
    this.validTo = builder.validTo;
    this.timeTableRelations = builder.timeTableRelations;
  }

  /**
   * Gets timetable id.
   *
   * @return the timetable id
   */
  public Long getTimetableId() {
    return timetableId;
  }

  /**
   * Sets timetable id.
   *
   * @param timetableId the timetable id
   */
  public void setTimetableId(Long timetableId) {
    this.timetableId = timetableId;
  }

  /**
   * Gets timetable name.
   *
   * @return the timetable name
   */
  public String getTimetableName() {
    return timetableName;
  }

  /**
   * Sets timetable name.
   *
   * @param timetableName the timetable name
   */
  public void setTimetableName(String timetableName) {
    this.timetableName = timetableName;
  }

  /**
   * Gets valid from.
   *
   * @return the valid from
   */
  public Date getValidFrom() {
    return validFrom;
  }

  /**
   * Sets valid from.
   *
   * @param validFrom the valid from
   */
  public void setValidFrom(Date validFrom) {
    this.validFrom = validFrom;
  }

  /**
   * Gets valid to.
   *
   * @return the valid to
   */
  public Date getValidTo() {
    return validTo;
  }

  /**
   * Sets valid to.
   *
   * @param validTo the valid to
   */
  public void setValidTo(Date validTo) {
    this.validTo = validTo;
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
   * Add time table entry.
   *
   * @param timeTableRelation the time table entry
   */
  public void addTimeTableRelation(TimeTableRelation timeTableRelation) {
    this.timeTableRelations.add(timeTableRelation);
  }

  /**
   * Remove time table entry.
   *
   * @param timeTableRelation the time table entry
   */
  public void removeTimeTableRelation(TimeTableRelation timeTableRelation) {
    this.timeTableRelations.remove(timeTableRelation);
  }

  /**
   * Remove time table entry.
   *
   * @param timeTableRelationId the time table entry id
   */
  public void removeTimeTableRelation(Long timeTableRelationId) {
    this.timeTableRelations.stream()
        .filter(timeTableEntry -> Objects.equals(timeTableEntry.getTimeTableRelationId(),
        timeTableRelationId))
        .findFirst()
        .ifPresent(this::removeTimeTableRelation);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TimeTable)) {
      return false;
    }
    TimeTable timeTable = (TimeTable) o;
    return Objects.equals(getTimetableId(), timeTable.getTimetableId())
        && Objects.equals(getTimetableName(), timeTable.getTimetableName())
        && Objects.equals(getValidFrom(), timeTable.getValidFrom())
        && Objects.equals(getValidTo(), timeTable.getValidTo())
        && Objects.equals(getTimeTableRelations(), timeTable.getTimeTableRelations());
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        getTimetableId(),
        getTimetableName(),
        getValidFrom(),
        getValidTo(),
        getTimeTableRelations());
  }


  /**
   * The type Time table builder.
   */
  public static final class TimeTableBuilder {
    private Long timetableId;
    private String timetableName;
    private Date validFrom;
    private Date validTo;
    private Collection<TimeTableRelation> timeTableRelations;

    /**
     * Sets timetable id.
     *
     * @param timetableId the timetable id
     * @return the timetable id
     */
    public TimeTableBuilder setTimetableId(Long timetableId) {
      this.timetableId = timetableId;
      return this;
    }

    /**
     * Sets timetable name.
     *
     * @param timetableName the timetable name
     * @return the timetable name
     */
    public TimeTableBuilder setTimetableName(String timetableName) {
      this.timetableName = timetableName;
      return this;
    }

    /**
     * Sets valid from.
     *
     * @param validFrom the valid from
     * @return the valid from
     */
    public TimeTableBuilder setValidFrom(Date validFrom) {
      this.validFrom = validFrom;
      return this;
    }

    /**
     * Sets valid to.
     *
     * @param validTo the valid to
     * @return the valid to
     */
    public TimeTableBuilder setValidTo(Date validTo) {
      this.validTo = validTo;
      return this;
    }

    /**
     * Sets time table entries.
     *
     * @param timeTableRelations the time table entries
     * @return the time table entries
     */
    public TimeTableBuilder setTimeTableEntries(Collection<TimeTableRelation> timeTableRelations) {
      this.timeTableRelations = timeTableRelations;
      return this;
    }

    /**
     * Build time table.
     *
     * @return the time table
     */
    public TimeTable build() {
      return new TimeTable(this);
    }
  }
}
