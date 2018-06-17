package open.schoolmanagement.time.calendartaskprojectservice.domain.project;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The type Project task self relation.
 */
@Entity
@Table(name = "projecttaskselfrelation")
public class ProjectTaskSelfRelation {


  @Id
  @GeneratedValue
  @Column(name = "projecttaskselfrelation_id")
  private Long projectTaskSelfRelationId;


  @OneToOne(mappedBy = "projecttask_id",
      fetch = FetchType.LAZY)
  private ProjectTask projectTask;


  @OneToOne(mappedBy = "predecessor_id",
      fetch = FetchType.LAZY)
  private ProjectTask predecessor;

  /**
   * Instantiates a new Project task self relation.
   *
   * @param builder the builder
   */
  public ProjectTaskSelfRelation(ProjectTaskBuilder builder) {

    this.projectTask = builder.projectTask;
    this.predecessor = builder.predecessor;
  }


  /**
   * Gets project task self relation id.
   *
   * @return the project task self relation id
   */
  public Long getProjectTaskSelfRelationId() {
    return projectTaskSelfRelationId;
  }

  /**
   * Sets project task self relation id.
   *
   * @param projectTaskSelfRelationId the project task self relation id
   */
  public void setProjectTaskSelfRelationId(Long projectTaskSelfRelationId) {
    this.projectTaskSelfRelationId = projectTaskSelfRelationId;
  }

  /**
   * Gets project task.
   *
   * @return the project task
   */
  public ProjectTask getProjectTask() {
    return projectTask;
  }

  /**
   * Sets project task.
   *
   * @param projectTask the project task
   */
  public void setProjectTask(ProjectTask projectTask) {
    this.projectTask = projectTask;
  }

  /**
   * Gets predecessor.
   *
   * @return the predecessor
   */
  public ProjectTask getPredecessor() {
    return predecessor;
  }

  /**
   * Sets predecessor.
   *
   * @param predecessor the predecessor
   */
  public void setPredecessor(ProjectTask predecessor) {
    this.predecessor = predecessor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProjectTaskSelfRelation)) {
      return false;
    }
    ProjectTaskSelfRelation that = (ProjectTaskSelfRelation) o;
    return Objects.equals(getProjectTaskSelfRelationId(), that.getProjectTaskSelfRelationId())
        && Objects.equals(getProjectTask(), that.getProjectTask())
        && Objects.equals(getPredecessor(), that.getPredecessor());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getProjectTaskSelfRelationId(), getProjectTask(), getPredecessor());
  }


  /**
   * The type Project task builder.
   */
  static final class ProjectTaskBuilder {

    private ProjectTask projectTask;
    private ProjectTask predecessor;

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
     * Sets project task.
     *
     * @param projectTask the project task
     * @return the project task
     */
    public ProjectTaskBuilder setProjectTask(ProjectTask projectTask) {
      this.projectTask = projectTask;
      return this;
    }

    /**
     * Sets predecessor.
     *
     * @param predecessor the predecessor
     * @return the predecessor
     */
    public ProjectTaskBuilder setPredecessor(ProjectTask predecessor) {
      this.predecessor = predecessor;
      return this;
    }

    /**
     * Build project task self relation.
     *
     * @return the project task self relation
     */
    public ProjectTaskSelfRelation build() {
      return new ProjectTaskSelfRelation(this);
    }
  }
}
