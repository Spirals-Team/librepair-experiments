package io.testrex.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User
 */
@Validated
@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-30T21:24:04.222Z")
public class User {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("project_roles")
  @Valid
  private List<ProjectRole> projectRoles = null;

  public User name(String name) {
    this.name = name;
    return this;
  }

  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  @NotNull
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User projectRoles(List<ProjectRole> projectRoles) {
    this.projectRoles = projectRoles;
    return this;
  }

  public User addProjectRolesItem(ProjectRole projectRolesItem) {
    if (this.projectRoles == null) {
      this.projectRoles = new ArrayList<ProjectRole>();
    }
    this.projectRoles.add(projectRolesItem);
    return this;
  }

  @Valid
  public List<ProjectRole> getProjectRoles() {
    return projectRoles;
  }

  public void setProjectRoles(List<ProjectRole> projectRoles) {
    this.projectRoles = projectRoles;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.name, user.name) && Objects.equals(this.email, user.email) && Objects.equals(this.projectRoles, user.projectRoles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email, projectRoles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    projectRoles: ").append(toIndentedString(projectRoles)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
