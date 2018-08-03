package com.cmpl.web.core.design;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class DesignDTOBuilder extends BaseBuilder<DesignDTO> {

  private Long websiteId;
  private Long styleId;

  public DesignDTOBuilder websiteId(Long websiteId) {
    this.websiteId = websiteId;
    return this;
  }

  public DesignDTOBuilder styleId(Long styleId) {
    this.styleId = styleId;
    return this;
  }

  private DesignDTOBuilder() {

  }

  @Override
  public DesignDTO build() {
    DesignDTO design = new DesignDTO();
    design.setStyleId(styleId);
    design.setWebsiteId(websiteId);
    design.setCreationDate(creationDate);
    design.setCreationUser(creationUser);
    design.setModificationUser(modificationUser);
    design.setId(id);
    design.setModificationDate(modificationDate);
    return design;
  }

  public static DesignDTOBuilder create() {
    return new DesignDTOBuilder();
  }
}
