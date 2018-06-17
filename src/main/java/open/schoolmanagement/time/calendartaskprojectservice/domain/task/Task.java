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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;

/**
 * The type Task.
 */
@Entity
@Table(name = "task")
public class Task {

  @Id
  @GeneratedValue
  @Column(name = "task_id", nullable = false)
  private Long taskId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "deadline")
  private Date deadline;

  @Column(name = "started")
  private Boolean started;

  @Column(name = "reminder")
  private Date reminder;

  @OneToOne(mappedBy = "taskgiver",
      fetch = FetchType.LAZY)
  private Person taskGiver;

  @OneToOne(mappedBy = "taskexecutor",
      fetch = FetchType.LAZY)
  private Person taskExecutor;

  private Task(TaskBuilder builder) {
    this.name = builder.name;
    this.description = builder.description;
    this.deadline = builder.deadline;
    this.started = builder.started;
    this.reminder = builder.reminder;
    this.taskGiver = builder.taskGiver;
    this.taskExecutor = builder.taskExecutor;
  }

  /**
   * Gets task id.
   *
   * @return the task id
   */
  public Long getTaskId() {
    return taskId;
  }

  /**
   * Sets task id.
   *
   * @param taskId the task id
   */
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
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
   * Gets deadline.
   *
   * @return the deadline
   */
  public Date getDeadline() {
    return deadline;
  }

  /**
   * Sets deadline.
   *
   * @param deadline the deadline
   */
  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  /**
   * Gets started.
   *
   * @return the started
   */
  public Boolean getStarted() {
    return started;
  }

  /**
   * Sets started.
   *
   * @param started the started
   */
  public void setStarted(Boolean started) {
    this.started = started;
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
   * Gets task giver.
   *
   * @return the task giver
   */
  public Person getTaskGiver() {
    return taskGiver;
  }

  /**
   * Sets task giver.
   *
   * @param taskGiver the task giver
   */
  public void setTaskGiver(Person taskGiver) {
    this.taskGiver = taskGiver;
  }

  /**
   * Gets task executor.
   *
   * @return the task executor
   */
  public Person getTaskExecutor() {
    return taskExecutor;
  }

  /**
   * Sets task executor.
   *
   * @param taskExecutor the task executor
   */
  public void setTaskExecutor(Person taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Task)) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(getTaskId(), task.getTaskId())
        && Objects.equals(getName(), task.getName())
        && Objects.equals(getDescription(), task.getDescription())
        && Objects.equals(getDeadline(), task.getDeadline())
        && Objects.equals(getStarted(), task.getStarted())
        && Objects.equals(getReminder(), task.getReminder())
        && Objects.equals(getTaskGiver(), task.getTaskGiver())
        && Objects.equals(getTaskExecutor(), task.getTaskExecutor());
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        getTaskId(),
        getName(),
        getDescription(),
        getDeadline(),
        getStarted(),
        getReminder(),
        getTaskGiver(),
        getTaskExecutor());
  }


  /**
   * The type Task builder.
   */
  public static final class TaskBuilder {
    private String name;
    private String description;
    private Date deadline;
    private Boolean started;
    private Date reminder;
    private Person taskGiver;
    private Person taskExecutor;


    private TaskBuilder() {

    }

    /**
     * Builder task builder.
     *
     * @return the task builder
     */
    public static TaskBuilder builder() {
      return new TaskBuilder();
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public TaskBuilder setName(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public TaskBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets deadline.
     *
     * @param deadline the deadline
     * @return the deadline
     */
    public TaskBuilder setDeadline(Date deadline) {
      this.deadline = deadline;
      return this;
    }

    /**
     * Sets started.
     *
     * @param started the started
     * @return the started
     */
    public TaskBuilder setStarted(Boolean started) {
      this.started = started;
      return this;
    }

    /**
     * Sets reminder.
     *
     * @param reminder the reminder
     * @return the reminder
     */
    public TaskBuilder setReminder(Date reminder) {
      this.reminder = reminder;
      return this;
    }

    /**
     * Sets task giver.
     *
     * @param taskGiver the task giver
     * @return the task giver
     */
    public TaskBuilder setTaskGiver(Person taskGiver) {
      this.taskGiver = taskGiver;
      return this;
    }

    /**
     * Sets task executor.
     *
     * @param taskExecutor the task executor
     * @return the task executor
     */
    public TaskBuilder setTaskExecutor(Person taskExecutor) {
      this.taskExecutor = taskExecutor;
      return this;
    }

    /**
     * Build task.
     *
     * @return the task
     */
    public Task build() {
      return new Task(this);
    }
  }
}
