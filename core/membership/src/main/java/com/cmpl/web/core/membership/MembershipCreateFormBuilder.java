package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.builder.Builder;

public class MembershipCreateFormBuilder extends Builder<MembershipCreateForm> {

  private String entityId;

  private String groupId;

  public MembershipCreateFormBuilder entityId(String entityId) {
    this.entityId = entityId;
    return this;
  }

  public MembershipCreateFormBuilder goupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  private MembershipCreateFormBuilder() {

  }

  @Override
  public MembershipCreateForm build() {
    MembershipCreateForm form = new MembershipCreateForm();
    form.setEntityId(entityId);
    form.setGroupId(groupId);
    return form;
  }

  public static MembershipCreateFormBuilder create() {
    return new MembershipCreateFormBuilder();
  }
}
