package com.cmpl.web.core.common.user;

import org.springframework.security.core.GrantedAuthority;

public class GroupGrantedAuthority implements GrantedAuthority {

  private long groupId;

  public GroupGrantedAuthority(long id) {
    this.groupId = id;
  }

  @Override
  public String getAuthority() {
    return String.valueOf(groupId);
  }

  public long getGroupId() {
    return groupId;
  }
}
