package com.cmpl.web.core.design;

import com.cmpl.web.core.common.builder.Builder;

public class DesignResponseBuilder extends Builder<DesignResponse> {

  private DesignDTO designDTO;

  public DesignResponseBuilder designDTO(DesignDTO designDTO) {
    this.designDTO = designDTO;
    return this;
  }

  private DesignResponseBuilder() {

  }

  @Override
  public DesignResponse build() {
    DesignResponse designResponse = new DesignResponse();
    designResponse.setDesignDTO(designDTO);
    return designResponse;
  }

  public static DesignResponseBuilder create() {
    return new DesignResponseBuilder();
  }
}
