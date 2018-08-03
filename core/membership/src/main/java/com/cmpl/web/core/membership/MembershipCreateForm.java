package com.cmpl.web.core.membership;

import javax.validation.constraints.NotBlank;

public class MembershipCreateForm {

  @NotBlank(message = "empty.entity.id")
  private String entityId;

  @NotBlank(message = "empty.group.id")
  private String groupId;

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
}
