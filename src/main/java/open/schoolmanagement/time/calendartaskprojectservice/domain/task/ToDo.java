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

package open.schoolmanagement.time.calendartaskprojectservice.domain.task;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;

/**
 * The type To do.
 */

@Entity
@Table(name = "todo")
public class ToDo {


  @Id
  @GeneratedValue
  @Column(name = "todo_id")
  private Long toDoId;


  @Column(name = "name")
  private String name;


  @Column(name = "duedate")
  private Date dueDate;


  @Column(name = "description")
  private String description;


  @Column(name = "reminder")
  private Date reminder;


  @JoinColumn(name = "todo_owner")
  @OneToOne(mappedBy = "person_id", fetch = FetchType.EAGER)
  private Person owner;

  private ToDo(ToDoBuilder builder) {
    this.name = builder.name;
    this.dueDate = builder.dueDate;
    this.description = builder.description;
    this.reminder = builder.reminder;
    this.owner = builder.owner;
  }

  /**
   * Gets to do id.
   *
   * @return the to do id
   */
  public Long getToDoId() {
    return toDoId;
  }

  /**
   * Sets to do id.
   *
   * @param toDoId the to do id
   */
  public void setToDoId(Long toDoId) {
    this.toDoId = toDoId;
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
   * Gets due date.
   *
   * @return the due date
   */
  public Date getDueDate() {
    return dueDate;
  }

  /**
   * Sets due date.
   *
   * @param dueDate the due date
   */
  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets reminder.
   *
   * @return the reminder
   */
  public Date getReminder() {
    return reminder;
  }

  /**
   * Sets reminder.
   *
   * @param reminder the reminder
   */
  public void setReminder(Date reminder) {
    this.reminder = reminder;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ToDo)) {
      return false;
    }
    ToDo toDo = (ToDo) o;
    return Objects.equals(getToDoId(), toDo.getToDoId())
        && Objects.equals(getName(), toDo.getName())
        && Objects.equals(getDueDate(), toDo.getDueDate())
        && Objects.equals(getDescription(), toDo.getDescription())
        && Objects.equals(getReminder(), toDo.getReminder())
        && Objects.equals(getOwner(), toDo.getOwner());
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        getToDoId(),
        getName(),
        getDueDate(),
        getDescription(),
        getReminder(),
        getOwner());
  }


  /**
   * The type To do builder.
   */
  public static final class ToDoBuilder {

    private String name;
    private Date dueDate;
    private String description;
    private Date reminder;
    private Person owner;

    private ToDoBuilder() {
    }

    /**
     * Builder to do builder.
     *
     * @return the to do builder
     */
    public ToDoBuilder builder() {
      return new ToDoBuilder();
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public ToDoBuilder setName(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets due date.
     *
     * @param dueDate the due date
     * @return the due date
     */
    public ToDoBuilder setDueDate(Date dueDate) {
      this.dueDate = dueDate;
      return this;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public ToDoBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets reminder.
     *
     * @param reminder the reminder
     * @return the reminder
     */
    public ToDoBuilder setReminder(Date reminder) {
      this.reminder = reminder;
      return this;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     * @return the owner
     */
    public ToDoBuilder setOwner(Person owner) {
      this.owner = owner;
      return this;
    }

    /**
     * Build to do.
     *
     * @return the to do
     */
    public ToDo build() {
      return new ToDo(this);
    }
  }
}
