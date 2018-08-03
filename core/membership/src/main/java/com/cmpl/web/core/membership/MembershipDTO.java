package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.dto.BaseDTO;

public class MembershipDTO extends BaseDTO {

  private Long entityId;

  private Long groupId;

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }
}
