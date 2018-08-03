package com.cmpl.web.core.design;

import com.cmpl.web.core.common.resource.BaseResponse;

public class DesignResponse extends BaseResponse {

  private DesignDTO designDTO;

  public DesignDTO getDesignDTO() {
    return designDTO;
  }

  public void setDesignDTO(DesignDTO designDTO) {
    this.designDTO = designDTO;
  }
}
