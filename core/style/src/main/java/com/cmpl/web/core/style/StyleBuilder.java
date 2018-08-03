package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Style;

public class StyleBuilder extends BaseBuilder<Style> {

  private String mediaId;
  private String name;

  private StyleBuilder() {

  }

  public StyleBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  public StyleBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Style build() {
    Style style = new Style();

    style.setMediaId(mediaId);
    style.setName(name);
    style.setCreationDate(creationDate);
    style.setCreationUser(creationUser);
    style.setModificationUser(modificationUser);
    style.setId(id);
    style.setModificationDate(modificationDate);

    return style;
  }

  public static StyleBuilder create() {
    return new StyleBuilder();
  }

}
