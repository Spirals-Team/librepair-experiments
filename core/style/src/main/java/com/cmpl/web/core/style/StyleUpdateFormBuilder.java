package com.cmpl.web.core.style;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class StyleUpdateFormBuilder extends Builder<StyleUpdateForm> {

  private String content;
  private String name;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String mediaName;
  private Long mediaId;
  private String creationUser;
  private String modificationUser;

  public StyleUpdateFormBuilder content(String content) {
    this.content = content;
    return this;
  }

  public StyleUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public StyleUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public StyleUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public StyleUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public StyleUpdateFormBuilder mediaName(String mediaName) {
    this.mediaName = mediaName;
    return this;
  }

  public StyleUpdateFormBuilder mediaId(Long mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  public StyleUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public StyleUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public StyleUpdateForm build() {
    StyleUpdateForm form = new StyleUpdateForm();
    form.setContent(content);
    form.setCreationDate(creationDate);
    form.setCreationUser(creationUser);
    form.setId(id);
    form.setMediaId(mediaId);
    form.setMediaName(mediaName);
    form.setModificationDate(modificationDate);
    form.setModificationUser(modificationUser);
    form.setName(name);
    return form;
  }

  public static StyleUpdateFormBuilder create() {
    return new StyleUpdateFormBuilder();
  }

}
