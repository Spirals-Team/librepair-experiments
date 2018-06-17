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

package open.schoolmanagement.time.calendartaskprojectservice.domain.lending;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;


/**
 * The type Lend object.
 */
@Entity
@Table(name = "lendboject")
public class LendObject {


  @Id
  @GeneratedValue
  private Long id;


  @Column(name = "name")
  private String name;


  @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
  private Person owner;


  @OneToOne(mappedBy = "lendbywhom", fetch = FetchType.LAZY)
  private Person lendByWhom;


  @Column(name = "whenlend")
  private Date whenLend;


  @Column(name = "duetoreturn")
  private Date dueToReturn;


  @Column(name = "returned")
  private boolean returned;

  private LendObject(LendObjectBuilder builder) {

    this.name = builder.name;
    this.owner = builder.owner;
    this.lendByWhom = builder.lendByWhom;
    this.whenLend = builder.whenLend;
    this.dueToReturn = builder.dueToReturn;
    this.returned = builder.returned;
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

  /**
   * Gets lend by whom.
   *
   * @return the lend by whom
   */
  public Person getLendByWhom() {
    return lendByWhom;
  }

  /**
   * Sets lend by whom.
   *
   * @param lendByWhom the lend by whom
   */
  public void setLendByWhom(Person lendByWhom) {
    this.lendByWhom = lendByWhom;
  }

  /**
   * Gets when lend.
   *
   * @return the when lend
   */
  public Date getWhenLend() {
    return whenLend;
  }

  /**
   * Sets when lend.
   *
   * @param whenLend the when lend
   */
  public void setWhenLend(Date whenLend) {
    this.whenLend = whenLend;
  }

  /**
   * Gets due to return.
   *
   * @return the due to return
   */
  public Date getDueToReturn() {
    return dueToReturn;
  }

  /**
   * Sets due to return.
   *
   * @param dueToReturn the due to return
   */
  public void setDueToReturn(Date dueToReturn) {
    this.dueToReturn = dueToReturn;
  }

  /**
   * Is returned boolean.
   *
   * @return the boolean
   */
  public boolean isReturned() {
    return returned;
  }

  /**
   * Sets returned.
   *
   * @param returned the returned
   */
  public void setReturned(boolean returned) {
    this.returned = returned;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LendObject)) {
      return false;
    }
    LendObject that = (LendObject) o;
    return isReturned() == that.isReturned()
        && Objects.equals(getId(), that.getId())
        && Objects.equals(getName(), that.getName())
        && Objects.equals(getOwner(), that.getOwner())
        && Objects.equals(getLendByWhom(), that.getLendByWhom())
        && Objects.equals(getWhenLend(), that.getWhenLend())
        && Objects.equals(getDueToReturn(), that.getDueToReturn());
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        getId(),
        getName(),
        getOwner(),
        getLendByWhom(),
        getWhenLend(),
        getDueToReturn(),
        isReturned());
  }


  /**
   * The type Lend object builder.
   */
  public static final class LendObjectBuilder {

    private String name;
    private Person owner;
    private Person lendByWhom;
    private Date whenLend;
    private Date dueToReturn;
    private boolean returned;

    /**
     * Builder lend object builder.
     *
     * @return the lend object builder
     */
    public static LendObjectBuilder builder() {
      return new LendObjectBuilder();
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public LendObjectBuilder setName(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     * @return the owner
     */
    public LendObjectBuilder setOwner(Person owner) {
      this.owner = owner;
      return this;
    }

    /**
     * Sets lend by whom.
     *
     * @param lendByWhom the lend by whom
     * @return the lend by whom
     */
    public LendObjectBuilder setLendByWhom(Person lendByWhom) {
      this.lendByWhom = lendByWhom;
      return this;
    }

    /**
     * Sets when lend.
     *
     * @param whenLend the when lend
     * @return the when lend
     */
    public LendObjectBuilder setWhenLend(Date whenLend) {
      this.whenLend = whenLend;
      return this;
    }

    /**
     * Sets due to return.
     *
     * @param dueToReturn the due to return
     * @return the due to return
     */
    public LendObjectBuilder setDueToReturn(Date dueToReturn) {
      this.dueToReturn = dueToReturn;
      return this;
    }

    /**
     * Sets returned.
     *
     * @param returned the returned
     * @return the returned
     */
    public LendObjectBuilder setReturned(boolean returned) {
      this.returned = returned;
      return this;
    }

    /**
     * Build lend object.
     *
     * @return the lend object
     */
    public LendObject build() {
      return new LendObject(this);
    }
  }
}
