package com.cmpl.web.core.group;

import com.cmpl.web.core.common.builder.Builder;

public class GroupResponseBuilder extends Builder<GroupResponse> {

  private GroupDTO group;

  public GroupResponseBuilder group(GroupDTO group) {
    this.group = group;
    return this;
  }

  private GroupResponseBuilder() {

  }

  @Override
  public GroupResponse build() {
    GroupResponse response = new GroupResponse();
    response.setGroup(group);

    return response;
  }

  public static GroupResponseBuilder create() {
    return new GroupResponseBuilder();
  }

}
