package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.resource.BaseResponse;

public class ResponsibilityResponse extends BaseResponse {

  private ResponsibilityDTO responsibilityDTO;

  public ResponsibilityDTO getResponsibilityDTO() {
    return responsibilityDTO;
  }

  public void setResponsibilityDTO(ResponsibilityDTO responsibilityDTO) {
    this.responsibilityDTO = responsibilityDTO;
  }
}
