package com.cmpl.web.core.media;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class MediaBuilder extends BaseBuilder<Media> {

  private String src;
  private String name;
  private String extension;
  private String contentType;
  private Long size;

  private MediaBuilder() {

  }

  public MediaBuilder src(String src) {
    this.src = src;
    return this;
  }

  public MediaBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MediaBuilder extension(String extension) {
    this.extension = extension;
    return this;
  }

  public MediaBuilder contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  public MediaBuilder size(Long size) {
    this.size = size;
    return this;
  }

  @Override
  public Media build() {

    Media media = new Media();
    media.setContentType(contentType);
    media.setCreationDate(creationDate);
    media.setExtension(extension);
    media.setId(id);
    media.setModificationDate(modificationDate);
    media.setName(name);
    media.setSize(size);
    media.setSrc(src);
    return media;
  }

  public static MediaBuilder create() {
    return new MediaBuilder();
  }

}
