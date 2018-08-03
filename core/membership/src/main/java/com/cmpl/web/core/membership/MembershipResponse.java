package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.resource.BaseResponse;

public class MembershipResponse extends BaseResponse {

  private MembershipDTO membership;

  public MembershipDTO getMembership() {
    return membership;
  }

  public void setMembership(MembershipDTO membership) {
    this.membership = membership;
  }
}
