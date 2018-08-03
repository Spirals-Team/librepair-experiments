package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.builder.Builder;

public class MembershipResponseBuilder extends Builder<MembershipResponse> {

  private MembershipDTO membership;

  private MembershipResponseBuilder() {

  }

  public MembershipResponseBuilder membership(MembershipDTO membership) {
    this.membership = membership;
    return this;
  }

  @Override
  public MembershipResponse build() {
    MembershipResponse response = new MembershipResponse();
    response.setMembership(membership);
    return response;
  }

  public static MembershipResponseBuilder create() {
    return new MembershipResponseBuilder();
  }
}
