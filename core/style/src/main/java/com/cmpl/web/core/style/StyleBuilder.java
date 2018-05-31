package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class StyleBuilder extends BaseBuilder<Style> {

  private String mediaId;

  private StyleBuilder() {

  }

  public StyleBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  @Override
  public Style build() {
    Style style = new Style();
    style.setCreationDate(creationDate);
    style.setId(id);
    style.setMediaId(mediaId);
    style.setModificationDate(modificationDate);
    return style;
  }

  public static StyleBuilder create() {
    return new StyleBuilder();
  }

}
