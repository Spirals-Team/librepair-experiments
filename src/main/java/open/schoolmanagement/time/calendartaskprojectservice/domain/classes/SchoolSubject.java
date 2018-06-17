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

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import open.schoolmanagement.time.calendartaskprojectservice.domain.calendar.timetable.SubjectType;


/**
 * The type School subject.
 */
@Entity
@Table(name = "schoolsubject")
public class SchoolSubject {

  @Id
  @GeneratedValue
  @Column(name = "schoolsubject_id")
  private Long schoolSubjectId;

  @Column(name = "schoolsubject_name")
  private String schoolSubjectName;

  @Column(name = "subjecttype")
  private SubjectType subjectType;


  private SchoolSubject(SchoolSubjectBuilder builder) {

    this.schoolSubjectName = builder.schoolSubjectName;
    this.subjectType = builder.subjectType;
  }

  /**
   * Gets school subject id.
   *
   * @return the school subject id
   */
  public Long getSchoolSubjectId() {
    return schoolSubjectId;
  }

  /**
   * Sets school subject id.
   *
   * @param schoolSubjectId the school subject id
   */
  public void setSchoolSubjectId(Long schoolSubjectId) {
    this.schoolSubjectId = schoolSubjectId;
  }

  /**
   * Gets school subject name.
   *
   * @return the school subject name
   */
  public String getSchoolSubjectName() {
    return schoolSubjectName;
  }

  /**
   * Sets school subject name.
   *
   * @param schoolSubjectName the school subject name
   */
  public void setSchoolSubjectName(String schoolSubjectName) {
    this.schoolSubjectName = schoolSubjectName;
  }

  /**
   * Gets subject type.
   *
   * @return the subject type
   */
  public SubjectType getSubjectType() {
    return subjectType;
  }

  /**
   * Sets subject type.
   *
   * @param subjectType the subject type
   */
  public void setSubjectType(SubjectType subjectType) {
    this.subjectType = subjectType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SchoolSubject)) {
      return false;
    }
    SchoolSubject that = (SchoolSubject) o;
    return Objects.equals(getSchoolSubjectId(), that.getSchoolSubjectId())
        && Objects.equals(getSchoolSubjectName(), that.getSchoolSubjectName())
        && getSubjectType() == that.getSubjectType();
  }

  @Override
  public int hashCode() {

    return Objects.hash(getSchoolSubjectId(), getSchoolSubjectName(), getSubjectType());
  }


  /**
   * The type School subject builder.
   */
  public static final class SchoolSubjectBuilder {
    private String schoolSubjectName;
    private SubjectType subjectType;


    /**
     * Builder school subject builder.
     *
     * @return the school subject builder
     */
    public static SchoolSubjectBuilder builder() {
      return new SchoolSubjectBuilder();
    }


    /**
     * Sets school subject name.
     *
     * @param schoolSubjectName the school subject name
     * @return the school subject name
     */
    public SchoolSubjectBuilder setSchoolSubjectName(String schoolSubjectName) {
      this.schoolSubjectName = schoolSubjectName;
      return this;
    }

    /**
     * Sets subject type.
     *
     * @param subjectType the subject type
     * @return the subject type
     */
    public SchoolSubjectBuilder setSubjectType(SubjectType subjectType) {
      this.subjectType = subjectType;
      return this;
    }

    /**
     * Build school subject.
     *
     * @return the school subject
     */
    public SchoolSubject build() {
      return new SchoolSubject(this);
    }
  }
}
