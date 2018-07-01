package open.schoolmanagement.time.calendartaskprojectservice.domain.classes;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The type Teacher subject relation.
 */
@Entity
@Table(name = "teachersubjectrelation")
public class TeacherSubjectRelation {

  @Id
  @GeneratedValue
  private Long teacherSubjectRelationId;

  @OneToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @OneToOne
  @JoinColumn(name = "schoolsubject_id")
  private SchoolSubject schoolSubject;

  /**
   * Instantiates a new Teacher subject relation.
   *
   * @param builder the builder
   */
  public TeacherSubjectRelation(TeacherSubjectRelationBuilder builder) {
    this.teacher = builder.teacher;
    this.schoolSubject = builder.schoolSubject;
  }

  /**
   * Gets teacher.
   *
   * @return the teacher
   */
  public Teacher getTeacher() {
    return teacher;
  }

  /**
   * Sets teacher.
   *
   * @param teacher the teacher
   */
  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  /**
   * Gets school subject.
   *
   * @return the school subject
   */
  public SchoolSubject getSchoolSubject() {
    return schoolSubject;
  }

  /**
   * Sets school subject.
   *
   * @param schoolSubject the school subject
   */
  public void setSchoolSubject(SchoolSubject schoolSubject) {
    this.schoolSubject = schoolSubject;
  }

  /**
   * Gets teacher subject relation id.
   *
   * @return the teacher subject relation id
   */
  public Long getTeacherSubjectRelationId() {

    return teacherSubjectRelationId;
  }

  /**
   * Sets teacher subject relation id.
   *
   * @param teacherSubjectRelationId the teacher subject relation id
   */
  public void setTeacherSubjectRelationId(Long teacherSubjectRelationId) {
    this.teacherSubjectRelationId = teacherSubjectRelationId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeacherSubjectRelation)) {
      return false;
    }
    TeacherSubjectRelation that = (TeacherSubjectRelation) o;
    return Objects.equals(getTeacherSubjectRelationId(), that.getTeacherSubjectRelationId())
        && Objects.equals(getTeacher(), that.getTeacher())
        && Objects.equals(getSchoolSubject(), that.getSchoolSubject());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getTeacherSubjectRelationId(), getTeacher(), getSchoolSubject());
  }


  /**
   * The type Teacher subject relation builder.
   */
  public static final class TeacherSubjectRelationBuilder {
    private Teacher teacher;
    private SchoolSubject schoolSubject;


    /**
     * Builder teacher subject relation builder.
     *
     * @return the teacher subject relation builder
     */
    public static TeacherSubjectRelationBuilder builder() {
      return new TeacherSubjectRelationBuilder();
    }

    /**
     * Sets teacher.
     *
     * @param teacher the teacher
     * @return the teacher
     */
    public TeacherSubjectRelationBuilder setTeacher(Teacher teacher) {
      this.teacher = teacher;
      return this;
    }

    /**
     * Sets school subject.
     *
     * @param schoolSubject the school subject
     * @return the school subject
     */
    public TeacherSubjectRelationBuilder setSchoolSubject(SchoolSubject schoolSubject) {
      this.schoolSubject = schoolSubject;
      return this;
    }

    /**
     * Build teacher subject relation.
     *
     * @return the teacher subject relation
     */
    public TeacherSubjectRelation build() {
      return new TeacherSubjectRelation(this);
    }
  }
}
