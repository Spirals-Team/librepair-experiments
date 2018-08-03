package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity(name = "responsibilities")
@Table(name = "responsibilities", indexes = {@Index(name = "IDX_USER", columnList = "user_id"),
    @Index(name = "IDX_ROLE", columnList = "role_id"), @Index(name = "IDX_ROLE_USER", columnList = "role_id,user_id")})
public class Responsibility extends BaseEntity {

  @Column(name = "user_id")
  private String userId;
  @Column(name = "role_id")
  private String roleId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
}
