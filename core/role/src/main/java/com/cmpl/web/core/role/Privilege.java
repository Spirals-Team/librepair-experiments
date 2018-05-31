package com.cmpl.web.core.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "privilege")
@Table(name = "privilege", indexes = {@Index(name = "IDX_PRIVILEGE", columnList = "role_id")})
public class Privilege extends BaseEntity {

  @Column(name = "role_id")
  private String roleId;

  @Column(name = "content")
  private String content;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
