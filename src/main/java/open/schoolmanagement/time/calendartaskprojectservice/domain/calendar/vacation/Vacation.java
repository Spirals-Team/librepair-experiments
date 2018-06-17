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

/**
 * The type Vacation.
 */
@Entity
@Table(name = "vacation")
public class Vacation {


  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "start")
  private Date start;
  @Column(name = "end")
  private Date end;
  @Column(name = "year")
  private int year;
  @OneToOne(mappedBy = "state", fetch = FetchType.LAZY)
  private State state;
  @Column(name = "slug")
  private String slug;

  private Vacation(VacationBuilder builder) {

    this.start = builder.start;
    this.end = builder.end;
    this.year = builder.year;
    this.state = builder.state;
    this.slug = builder.slug;
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
   * Gets year.
   *
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Sets year.
   *
   * @param year the year
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Gets state.
   *
   * @return the state
   */
  public State getState() {
    return state;
  }

  /**
   * Sets state.
   *
   * @param state the state
   */
  public void setState(State state) {
    this.state = state;
  }

  /**
   * Gets slug.
   *
   * @return the slug
   */
  public String getSlug() {
    return slug;
  }

  /**
   * Sets slug.
   *
   * @param slug the slug
   */
  public void setSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Vacation)) {
      return false;
    }
    Vacation vacation = (Vacation) o;
    return getYear() == vacation.getYear()
        && Objects.equals(getId(), vacation.getId())
        && Objects.equals(getStart(), vacation.getStart())
        && Objects.equals(getEnd(), vacation.getEnd())
        && Objects.equals(getState(), vacation.getState())
        && Objects.equals(getSlug(), vacation.getSlug());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(), getStart(), getEnd(), getYear(), getState(), getSlug());
  }


  /**
   * The type Vacation builder.
   */
  static final class VacationBuilder {

    private Date start;
    private Date end;
    private int year;
    private State state;
    private String slug;


    /**
     * Sets start.
     *
     * @param start the start
     * @return the start
     */
    public VacationBuilder setStart(Date start) {
      this.start = start;
      return this;
    }

    /**
     * Sets end.
     *
     * @param end the end
     * @return the end
     */
    public VacationBuilder setEnd(Date end) {
      this.end = end;
      return this;
    }

    /**
     * Sets year.
     *
     * @param year the year
     * @return the year
     */
    public VacationBuilder setYear(int year) {
      this.year = year;
      return this;
    }

    /**
     * Sets state.
     *
     * @param state the state
     * @return the state
     */
    public VacationBuilder setState(State state) {
      this.state = state;
      return this;
    }

    /**
     * Sets slug.
     *
     * @param slug the slug
     * @return the slug
     */
    public VacationBuilder setSlug(String slug) {
      this.slug = slug;
      return this;
    }

    /**
     * Build vacation.
     *
     * @return the vacation
     */
    public Vacation build() {
      return new Vacation(this);
    }
  }
}
