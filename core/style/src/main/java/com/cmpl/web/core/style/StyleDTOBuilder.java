package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.media.MediaDTO;

public class StyleDTOBuilder extends BaseBuilder<StyleDTO> {

  private String content;
  private MediaDTO media;

  private StyleDTOBuilder() {

  }

  public StyleDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  public StyleDTOBuilder media(MediaDTO media) {
    this.media = media;
    return this;
  }

  @Override
  public StyleDTO build() {
    StyleDTO style = new StyleDTO();
    style.setContent(content);
    style.setCreationDate(creationDate);
    style.setId(id);
    style.setMedia(media);
    style.setModificationDate(modificationDate);
    return style;
  }

  public static StyleDTOBuilder create() {
    return new StyleDTOBuilder();
  }

}
