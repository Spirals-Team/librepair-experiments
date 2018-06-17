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

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;


/**
 * The type Project.
 */
@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue
  @Column(name = "project_id")
  private Long projectId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "responsible")
  private Person responsible;

  @Column(name = "project_start")
  private Date start;

  @Column(name = "project_end")
  private Date end;

  private Project(ProjectBuilder builder) {

    this.name = builder.name;
    this.description = builder.description;
    this.responsible = builder.responsible;
    this.start = builder.start;
    this.end = builder.end;
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
   * Gets responsible.
   *
   * @return the responsible
   */
  public Person getResponsible() {
    return responsible;
  }

  /**
   * Sets responsible.
   *
   * @param responsible the responsible
   */
  public void setResponsible(Person responsible) {
    this.responsible = responsible;
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
   * Gets project id.
   *
   * @return the project id
   */
  public Long getProjectId() {

    return projectId;
  }

  /**
   * Sets project id.
   *
   * @param projectId the project id
   */
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Project)) {
      return false;
    }
    Project project = (Project) o;
    return Objects.equals(getProjectId(), project.getProjectId())
        && Objects.equals(getStart(), project.getStart())
        && Objects.equals(getEnd(), project.getEnd());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getProjectId(), getStart(), getEnd());
  }


  /**
   * The type Project builder.
   */
  public static final class ProjectBuilder {

    private String name;
    private String description;
    private Person responsible;
    private Date start;
    private Date end;

    private ProjectBuilder() {

    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public ProjectBuilder setName(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public ProjectBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets responsible.
     *
     * @param responsible the responsible
     * @return the responsible
     */
    public ProjectBuilder setResponsible(Person responsible) {
      this.responsible = responsible;
      return this;
    }

    /**
     * Sets start.
     *
     * @param start the start
     * @return the start
     */
    public ProjectBuilder setStart(Date start) {
      this.start = start;
      return this;
    }

    /**
     * Sets end.
     *
     * @param end the end
     * @return the end
     */
    public ProjectBuilder setEnd(Date end) {
      this.end = end;
      return this;
    }

    /**
     * Build project.
     *
     * @return the project
     */
    public Project build() {
      return new Project(this);
    }
  }
}
