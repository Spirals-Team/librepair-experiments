package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.media.MediaDTO;

public class StyleDTOBuilder extends BaseBuilder<StyleDTO> {

  private String content;
  private String name;
  private MediaDTO media;

  private StyleDTOBuilder() {

  }

  public StyleDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  public StyleDTOBuilder name(String name) {
    this.name = name;
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
    style.setName(name);
    style.setMedia(media);
    style.setCreationDate(creationDate);
    style.setCreationUser(creationUser);
    style.setModificationUser(modificationUser);
    style.setId(id);
    style.setModificationDate(modificationDate);
    return style;
  }

  public static StyleDTOBuilder create() {
    return new StyleDTOBuilder();
  }

}
