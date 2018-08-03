package com.cmpl.web.core.design;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Design;

public class DesignBuilder extends BaseBuilder<Design> {

  private Long websiteId;
  private Long styleId;

  public DesignBuilder websiteId(Long websiteId) {
    this.websiteId = websiteId;
    return this;
  }

  public DesignBuilder styleId(Long styleId) {
    this.styleId = styleId;
    return this;
  }

  private DesignBuilder() {

  }

  @Override
  public Design build() {
    Design design = new Design();
    design.setStyleId(styleId);
    design.setWebsiteId(websiteId);
    design.setCreationDate(creationDate);
    design.setCreationUser(creationUser);
    design.setModificationUser(modificationUser);
    design.setId(id);
    design.setModificationDate(modificationDate);
    return design;
  }

  public static DesignBuilder create() {
    return new DesignBuilder();
  }
}
