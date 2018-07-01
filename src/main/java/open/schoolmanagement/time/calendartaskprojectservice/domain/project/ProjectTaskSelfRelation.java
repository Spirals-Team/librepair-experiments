package open.schoolmanagement.time.calendartaskprojectservice.domain.project;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "projecttask_id")
  private ProjectTask projectTask;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "predecessor_id")
  private ProjectTask predecessor;

  /**
   * Instantiates a new Project task self relation.
   *
   * @param builder the builder
   */
  private ProjectTaskSelfRelation(ProjectTaskSelfRelationBuilder builder) {

    this.projectTask = builder.projectTask;
    this.predecessor = builder.predecessor;
  }


  public ProjectTaskSelfRelationBuilder builder() {
    return new ProjectTaskSelfRelationBuilder();
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
  public static final class ProjectTaskSelfRelationBuilder {

    private ProjectTask projectTask;
    private ProjectTask predecessor;

    private ProjectTaskSelfRelationBuilder() {

    }


    /**
     * Sets project task.
     *
     * @param projectTask the project task
     * @return the project task
     */
    public ProjectTaskSelfRelationBuilder setProjectTask(ProjectTask projectTask) {
      this.projectTask = projectTask;
      return this;
    }

    /**
     * Sets predecessor.
     *
     * @param predecessor the predecessor
     * @return the predecessor
     */
    public ProjectTaskSelfRelationBuilder setPredecessor(ProjectTask predecessor) {
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
