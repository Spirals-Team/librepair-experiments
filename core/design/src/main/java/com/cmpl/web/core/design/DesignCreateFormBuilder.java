package com.cmpl.web.core.design;

import com.cmpl.web.core.common.builder.Builder;

public class DesignCreateFormBuilder extends Builder<DesignCreateForm> {

  private String websiteId;
  private String styleId;

  public DesignCreateFormBuilder websiteId(String websiteId) {
    this.websiteId = websiteId;
    return this;
  }

  public DesignCreateFormBuilder styleId(String styleId) {
    this.styleId = styleId;
    return this;
  }

  private DesignCreateFormBuilder() {

  }

  @Override
  public DesignCreateForm build() {
    DesignCreateForm form = new DesignCreateForm();
    form.setWebsiteId(websiteId);
    form.setStyleId(styleId);
    return form;
  }

  public static DesignCreateFormBuilder create() {
    return new DesignCreateFormBuilder();
  }
}
