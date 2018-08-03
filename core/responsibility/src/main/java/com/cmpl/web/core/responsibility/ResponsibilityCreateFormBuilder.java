package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.builder.Builder;

public class ResponsibilityCreateFormBuilder extends Builder<ResponsibilityCreateForm> {

  private String userId;
  private String roleId;

  public ResponsibilityCreateFormBuilder userId(String userId) {
    this.userId = userId;
    return this;
  }

  public ResponsibilityCreateFormBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  private ResponsibilityCreateFormBuilder() {

  }

  @Override
  public ResponsibilityCreateForm build() {
    ResponsibilityCreateForm form = new ResponsibilityCreateForm();
    form.setRoleId(roleId);
    form.setUserId(userId);
    return form;
  }

  public static ResponsibilityCreateFormBuilder create() {
    return new ResponsibilityCreateFormBuilder();
  }
}
