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
 * ProjectRole
 */
@Validated
@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-30T21:24:04.222Z")
public class ProjectRole {
  @JsonProperty("project")
  private String project = null;

  @JsonProperty("roles")
  @Valid
  private List<String> roles = new ArrayList<String>();

  public ProjectRole project(String project) {
    this.project = project;
    return this;
  }

  @NotNull
  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public ProjectRole roles(List<String> roles) {
    this.roles = roles;
    return this;
  }

  public ProjectRole addRolesItem(String rolesItem) {
    this.roles.add(rolesItem);
    return this;
  }

  @NotNull
  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectRole projectRole = (ProjectRole) o;
    return Objects.equals(this.project, projectRole.project) && Objects.equals(this.roles, projectRole.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(project, roles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectRole {\n");

    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
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
