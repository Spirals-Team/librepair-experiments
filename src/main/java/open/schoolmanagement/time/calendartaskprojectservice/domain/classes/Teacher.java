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

package open.schoolmanagement.time.calendartaskprojectservice.domain.classes;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.person.Person;

/**
 * The type Teacher.
 */
@Entity
@Table(name = "teacher")
public class Teacher {

  @Id
  @GeneratedValue
  @Column(name = "teacher_id")
  private Long teacherId;

  @OneToOne(mappedBy = "person")
  @JoinColumn(name = "person_id")
  private Person person;

  @OneToMany(mappedBy = "teacher_id", fetch = FetchType.LAZY)
  private Collection<TeacherSubjectRelation> subjectRelations;

  /**
   * Instantiates a new Teacher.
   *
   * @param builder the builder
   */
  public Teacher(TeacherBuilder builder) {
    this.person = builder.person;
    this.subjectRelations = builder.subjectRelations;
  }

  /**
   * Gets teacher id.
   *
   * @return the teacher id
   */
  public Long getTeacherId() {
    return teacherId;
  }

  /**
   * Sets teacher id.
   *
   * @param teacherId the teacher id
   */
  public void setTeacherId(Long teacherId) {
    this.teacherId = teacherId;
  }

  /**
   * Gets person.
   *
   * @return the person
   */
  public Person getPerson() {
    return person;
  }

  /**
   * Sets person.
   *
   * @param person the person
   */
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * Gets subject relations.
   *
   * @return the subject relations
   */
  public Collection<TeacherSubjectRelation> getSubjectRelations() {
    return subjectRelations;
  }

  /**
   * Sets subject relations.
   *
   * @param subjectRelations the subject relations
   */
  public void setSubjectRelations(Collection<TeacherSubjectRelation> subjectRelations) {
    this.subjectRelations = subjectRelations;
  }

  /**
   * Add school subject relation.
   *
   * @param subjectRelation the subject relation
   */
  public void addSchoolSubjectRelation(TeacherSubjectRelation subjectRelation) {
    this.subjectRelations.add(subjectRelation);
  }

  /**
   * Remove school subject relation.
   *
   * @param subjectRelation the subject relation
   */
  public void removeSchoolSubjectRelation(TeacherSubjectRelation subjectRelation) {
    this.subjectRelations.remove(subjectRelation);
  }

  /**
   * Remove school subject relation.
   *
   * @param subjectRelationId the subject relation id
   */
  public void removeSchoolSubjectRelation(Long subjectRelationId) {
    this.subjectRelations
        .stream()
        .filter(subjectRelation -> Objects.equals(
            subjectRelation.getTeacherSubjectRelationId(), subjectRelationId))
        .findFirst()
        .ifPresent(this::removeSchoolSubjectRelation);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Teacher)) {
      return false;
    }
    Teacher teacher = (Teacher) o;
    return Objects.equals(getTeacherId(), teacher.getTeacherId())
        && Objects.equals(getPerson(), teacher.getPerson());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getTeacherId(), getPerson());
  }


  /**
   * The type Teacher builder.
   */
  public static final class TeacherBuilder {
    private Person person;
    private Collection<TeacherSubjectRelation> subjectRelations;

    /**
     * Builder teacher builder.
     *
     * @return the teacher builder
     */
    public static TeacherBuilder builder() {
      return new TeacherBuilder();
    }


    /**
     * Sets person.
     *
     * @param person the person
     * @return the person
     */
    public TeacherBuilder setPerson(Person person) {
      this.person = person;
      return this;
    }

    /**
     * Sets subject relations.
     *
     * @param subjectRelations the subject relations
     * @return the subject relations
     */
    public TeacherBuilder setSubjectRelations(Collection<TeacherSubjectRelation> subjectRelations) {
      this.subjectRelations = subjectRelations;
      return this;
    }

    /**
     * Build teacher.
     *
     * @return the teacher
     */
    public Teacher build() {
      return new Teacher(this);
    }
  }
}
