package com.cmpl.web.core.association_user_role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "associationUserRole")
@Table(name = "association_user_role", indexes = {@Index(name = "IDX_USER", columnList = "user_id"),
    @Index(name = "IDX_ROLE", columnList = "role_id")})
public class AssociationUserRole extends BaseEntity {

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
