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

package open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.vacation;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.Constants;


/**
 * The type Public holiday.
 */
@Entity
@Table(name = "publicholiday")
public class PublicHoliday implements Constants {


  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "day")
  private Date day;

  @Column(name = "name")
  private String name;

  @OneToOne(mappedBy = "state",
      fetch = FetchType.LAZY)
  private State affectedCountryInStateCode;

  /**
   * Instantiates a new Public holiday.
   *
   * @param builder the builder
   */
  public PublicHoliday(PublicHolidayBuilder builder) {
    this.id = builder.id;
    this.day = builder.day;
    this.name = builder.name;
    this.affectedCountryInStateCode = builder.affectedCountryInStateCode;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets day.
   *
   * @return the day
   */
  public Date getDay() {
    return day;
  }

  /**
   * Sets day.
   *
   * @param day the day
   */
  public void setDay(Date day) {
    this.day = day;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets affected country in state code.
   *
   * @return the affected country in state code
   */
  public State getAffectedCountryInStateCode() {
    return affectedCountryInStateCode;
  }

  /**
   * Sets affected country in state code.
   *
   * @param affectedCountryInStateCode the affected country in state code
   */
  public void setAffectedCountryInStateCode(State affectedCountryInStateCode) {
    this.affectedCountryInStateCode = affectedCountryInStateCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PublicHoliday)) {
      return false;
    }
    PublicHoliday that = (PublicHoliday) o;
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getDay(), that.getDay())
        && Objects.equals(getName(), that.getName())
        && Objects.equals(getAffectedCountryInStateCode(), that.getAffectedCountryInStateCode());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(), getDay(), getName(), getAffectedCountryInStateCode());
  }


  /**
   * The type Public holiday builder.
   */
  public static final class PublicHolidayBuilder {
    private Long id;
    private Date day;
    private String name;
    private State affectedCountryInStateCode;

    /**
     * Sets id.
     *
     * @param id the id
     * @return the id
     */
    public PublicHolidayBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    /**
     * Sets day.
     *
     * @param day the day
     * @return the day
     */
    public PublicHolidayBuilder setDay(Date day) {
      this.day = day;
      return this;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public PublicHolidayBuilder setName(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets affected country in state code.
     *
     * @param affectedCountryInStateCode the affected country in state code
     * @return the affected country in state code
     */
    public PublicHolidayBuilder setAffectedCountryInStateCode(State affectedCountryInStateCode) {
      this.affectedCountryInStateCode = affectedCountryInStateCode;
      return this;
    }

    /**
     * Build public holiday.
     *
     * @return the public holiday
     */
    public PublicHoliday build() {
      return new PublicHoliday(this);
    }
  }
}
