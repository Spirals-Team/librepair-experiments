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

package open.schoolmanagement.time.calendartaskprojectservice.domain.project;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.task.Task;


/**
 * The type Project task.
 */
@Entity
@Table(name = "projecttask")
public class ProjectTask {


  @Id
  @GeneratedValue
  @Column(name = "projecttask_id")
  private Long projectTaskId;


  @OneToOne(mappedBy = "project_id",
      fetch = FetchType.LAZY)
  private Project project;


  @OneToOne(mappedBy = "task_id",
      fetch = FetchType.LAZY)
  private Task task;

  /**
   * Instantiates a new Project task.
   *
   * @param builder the builder
   */
  public ProjectTask(ProjectTaskBuilder builder) {

    this.project = builder.project;
    this.task = builder.task;
  }


  /**
   * Gets project task id.
   *
   * @return the project task id
   */
  public Long getProjectTaskId() {
    return projectTaskId;
  }

  /**
   * Sets project task id.
   *
   * @param projectTaskId the project task id
   */
  public void setProjectTaskId(Long projectTaskId) {
    this.projectTaskId = projectTaskId;
  }

  /**
   * Gets project.
   *
   * @return the project
   */
  public Project getProject() {
    return project;
  }

  /**
   * Sets project.
   *
   * @param project the project
   */
  public void setProject(Project project) {
    this.project = project;
  }

  /**
   * Gets task.
   *
   * @return the task
   */
  public Task getTask() {
    return task;
  }

  /**
   * Sets task.
   *
   * @param task the task
   */
  public void setTask(Task task) {
    this.task = task;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProjectTask)) {
      return false;
    }
    ProjectTask that = (ProjectTask) o;
    return Objects.equals(getProjectTaskId(), that.getProjectTaskId())
        && Objects.equals(getProject(), that.getProject())
        && Objects.equals(getTask(), that.getTask());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getProjectTaskId(), getProject(), getTask());
  }


  /**
   * The type Project task builder.
   */
  public static final class ProjectTaskBuilder {

    private Project project;
    private Task task;

    private ProjectTaskBuilder() {

    }

    /**
     * Builder project task builder.
     *
     * @return the project task builder
     */
    public ProjectTaskBuilder builder() {
      return new ProjectTaskBuilder();
    }

    /**
     * Sets project.
     *
     * @param project the project
     * @return the project
     */
    public ProjectTaskBuilder setProject(Project project) {
      this.project = project;
      return this;
    }

    /**
     * Sets task.
     *
     * @param task the task
     * @return the task
     */
    public ProjectTaskBuilder setTask(Task task) {
      this.task = task;
      return this;
    }

    /**
     * Build project task.
     *
     * @return the project task
     */
    public ProjectTask build() {
      return new ProjectTask(this);
    }
  }
}
