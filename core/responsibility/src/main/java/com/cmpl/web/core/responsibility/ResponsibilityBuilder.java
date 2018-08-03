package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Responsibility;

public class ResponsibilityBuilder extends BaseBuilder<Responsibility> {

  private String userId;
  private String roleId;

  public ResponsibilityBuilder userId(String userId) {
    this.userId = userId;
    return this;
  }

  public ResponsibilityBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  private ResponsibilityBuilder() {

  }

  @Override
  public Responsibility build() {
    Responsibility responsibility = new Responsibility();
    responsibility.setRoleId(roleId);
    responsibility.setUserId(userId);
    responsibility.setCreationDate(creationDate);
    responsibility.setCreationUser(creationUser);
    responsibility.setModificationUser(modificationUser);
    responsibility.setId(id);
    responsibility.setModificationDate(modificationDate);
    return responsibility;
  }

  public static ResponsibilityBuilder create() {
    return new ResponsibilityBuilder();
  }
}
