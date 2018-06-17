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

package open.schoolmanagement.time.calendartaskprojectservice.domain.person;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The type Person.
 */
@Entity
@Table(name = "person")
public class Person {


  @Id
  @GeneratedValue
  @Column(name = "person_id", nullable = false)
  private Long personId;


  @Column(name = "emailaddress")
  private String emailAddress;


  @Column(name = "firstname")
  private String firstName;


  @Column(name = "lastname")
  private String lastName;

  /**
   * Instantiates a new Person.
   *
   * @param builder the builder
   */
  public Person(PersonBuilder builder) {

    this.emailAddress = builder.emailAddress;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
  }

  /**
   * Gets person id.
   *
   * @return the person id
   */
  public Long getPersonId() {
    return personId;
  }

  /**
   * Sets person id.
   *
   * @param personId the person id
   */
  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  /**
   * Gets email address.
   *
   * @return the email address
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * Sets email address.
   *
   * @param emailAddress the email address
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets last name.
   *
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Person)) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(getPersonId(), person.getPersonId())
        && Objects.equals(getEmailAddress(), person.getEmailAddress())
        && Objects.equals(getFirstName(), person.getFirstName())
        && Objects.equals(getLastName(), person.getLastName());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getPersonId(), getEmailAddress(), getFirstName(), getLastName());
  }

  /**
   * Builder person builder.
   *
   * @return the person builder
   */
  public static PersonBuilder builder() {
    return new PersonBuilder();
  }

  /**
   * The type Person builder.
   */
  public static final class PersonBuilder {

    private String emailAddress;
    private String firstName;
    private String lastName;


    private PersonBuilder() {
    }

    ;




    /**
     * Sets email address.
     *
     * @param emailAddress the email address
     * @return the email address
     */
    public PersonBuilder setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
      return this;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     * @return the first name
     */
    public PersonBuilder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     * @return the last name
     */
    public PersonBuilder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    /**
     * Build person.
     *
     * @return the person
     */
    public Person build() {
      return new Person(this);
    }
  }
}
