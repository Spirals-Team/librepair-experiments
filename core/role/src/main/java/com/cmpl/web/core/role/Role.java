package com.cmpl.web.core.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "role")
@Table(name = "role")
public class Role extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
