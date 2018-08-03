package com.cmpl.web.core.group;

import com.cmpl.web.core.common.resource.BaseResponse;

public class GroupResponse extends BaseResponse {

  private GroupDTO group;

  public GroupDTO getGroup() {
    return group;
  }

  public void setGroup(GroupDTO group) {
    this.group = group;
  }
}
