package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.builder.Builder;

public class ResponsibilityResponseBuilder extends Builder<ResponsibilityResponse> {

  private ResponsibilityDTO responsibilityDTO;

  public ResponsibilityResponseBuilder associationUserRoleDTO(ResponsibilityDTO responsibilityDTO) {
    this.responsibilityDTO = responsibilityDTO;
    return this;
  }

  private ResponsibilityResponseBuilder() {

  }

  @Override
  public ResponsibilityResponse build() {
    ResponsibilityResponse responsibilityResponse = new ResponsibilityResponse();
    responsibilityResponse.setResponsibilityDTO(responsibilityDTO);
    return responsibilityResponse;
  }

  public static ResponsibilityResponseBuilder create() {
    return new ResponsibilityResponseBuilder();
  }
}
