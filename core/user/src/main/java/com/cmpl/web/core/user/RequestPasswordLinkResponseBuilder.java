package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;

public class RequestPasswordLinkResponseBuilder extends Builder<RequestPasswordLinkResponse> {

  private RequestPasswordLinkResponseBuilder() {

  }

  @Override
  public RequestPasswordLinkResponse build() {
    return new RequestPasswordLinkResponse();
  }

  public static RequestPasswordLinkResponseBuilder create() {
    return new RequestPasswordLinkResponseBuilder();
  }
}
