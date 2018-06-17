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

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type State.
 */
@Entity
@Table(name = "state")
public class State {


  /* *
   * Bundesland    Code
   * Baden-Württemberg Baden-Württemberg  DE-BW
   * Bayern Bayern   DE-BY
   * Berlin Berlin  DE-BE
   * Brandenburg Brandenburg DE-BB
   * Bremen Bremen  DE-HB
   * Hamburg Hamburg DE-HH
   * Hessen Hessen DE-HE
   * Mecklenburg-Vorpommern Mecklenburg-Vorpommern DE-MV
   * Niedersachsen Niedersachsen DE-NI
   * Nordrhein-Westfalen Nordrhein-Westfalen DE-NW
   * Rheinland-Pfalz Rheinland-Pfalz DE-RP
   * Saarland Saarland DE-SL
   * Sachsen Sachsen DE-SN
   * Sachsen-Anhalt Sachsen-Anhalt  DE-ST
   * Schleswig-Holstein Schleswig-Holstein DE-SH
   * Thüringen Thüringen DE-TH
   *
   * https://de.wikipedia.org/wiki/ISO_3166-2
   * https://de.wikipedia.org/wiki/ISO_3166#ISO_3166-1
   *
   * Also Check https://www.ferien-api.de/
   * */


  /**
   * The Name.
   */
  @Column(name = "name")
  String name; //Saarland
  /**
   * The Iso code.
   */
  @Column(name = "isocode")
  String isoCode; //DE-SL
  /**
   * The Iso code short.
   */
  @Column(name = "isocodeshort")
  String isoCodeShort; //SL
  @Id
  @GeneratedValue
  private Long id;

  private State(StateBuilder builder) {

    this.name = builder.name;
    this.isoCode = builder.isoCode;
    this.isoCodeShort = builder.isoCodeShort;
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
   * Gets iso code.
   *
   * @return the iso code
   */
  public String getIsoCode() {
    return isoCode;
  }

  /**
   * Sets iso code.
   *
   * @param isoCode the iso code
   */
  public void setIsoCode(String isoCode) {
    this.isoCode = isoCode;
  }

  /**
   * Gets iso code short.
   *
   * @return the iso code short
   */
  public String getIsoCodeShort() {
    return isoCodeShort;
  }

  /**
   * Sets iso code short.
   *
   * @param isoCodeShort the iso code short
   */
  public void setIsoCodeShort(String isoCodeShort) {
    this.isoCodeShort = isoCodeShort;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof State)) {
      return false;
    }
    State state = (State) o;
    return Objects.equals(getId(), state.getId())
        && Objects.equals(getName(), state.getName())
        && Objects.equals(getIsoCode(), state.getIsoCode());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(), getName(), getIsoCode());
  }


  /**
   * The type State builder.
   */
  public static final class StateBuilder {

    private String name;
    private String isoCode;
    private String isoCodeShort;

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * Sets iso code.
     *
     * @param isoCode the iso code
     * @return the iso code
     */
    public StateBuilder setIsoCode(String isoCode) {
      this.isoCode = isoCode;
      return this;
    }

    /**
     * Sets iso code short.
     *
     * @param isoCodeShort the iso code short
     * @return the iso code short
     */
    public StateBuilder setIsoCodeShort(String isoCodeShort) {
      this.isoCodeShort = isoCodeShort;
      return this;
    }

    /**
     * Build state.
     *
     * @return the state
     */
    public State build() {
      return new State(this);
    }
  }
}
