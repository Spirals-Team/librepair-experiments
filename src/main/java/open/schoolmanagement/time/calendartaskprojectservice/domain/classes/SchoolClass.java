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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The type School class.
 */
@Entity
@Table(name = "schoolclass")
public class SchoolClass {


  @Id
  @GeneratedValue
  @Column(name = "schoolclass_id")
  private Long schoolClassId;

  @Column(name = "schoolclass_name")
  private String name;

  @OneToOne
  @JoinColumn(name = "schoolclassteacher")
  private Teacher classTeacher;

  private SchoolClass(SchoolClassBuilder builder) {

    this.name = builder.name;
    this.classTeacher = builder.classTeacher;
  }


  public static SchoolClassBuilder builder() {
    return new SchoolClassBuilder();
  }

  /**
   * Gets school class id.
   *
   * @return the school class id
   */
  public Long getSchoolClassId() {
    return schoolClassId;
  }

  /**
   * Sets school class id.
   *
   * @param schoolClassId the school class id
   */
  public void setSchoolClassId(Long schoolClassId) {
    this.schoolClassId = schoolClassId;
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
   * Gets class teacher.
   *
   * @return the class teacher
   */
  public Teacher getClassTeacher() {
    return classTeacher;
  }

  /**
   * Sets class teacher.
   *
   * @param classTeacher the class teacher
   */
  public void setClassTeacher(Teacher classTeacher) {
    this.classTeacher = classTeacher;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SchoolClass)) {
      return false;
    }
    SchoolClass that = (SchoolClass) o;
    return Objects.equals(getSchoolClassId(), that.getSchoolClassId())
        && Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getSchoolClassId(), getName());
  }


  public static final class SchoolClassBuilder {

    private String name;
    private Teacher classTeacher;

    private SchoolClassBuilder(){

    }


    public SchoolClassBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public SchoolClassBuilder setClassTeacher(Teacher classTeacher) {
      this.classTeacher = classTeacher;
      return this;
    }

    public SchoolClass build() {
      return new SchoolClass(this);
    }
  }
}
