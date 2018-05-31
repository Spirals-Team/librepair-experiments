package com.cmpl.web.core.media;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class MediaDTOBuilder extends BaseBuilder<MediaDTO> {

  private String src;
  private String name;
  private String extension;
  private String contentType;
  private Long size;

  private MediaDTOBuilder() {

  }

  public MediaDTOBuilder src(String src) {
    this.src = src;
    return this;
  }

  public MediaDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public MediaDTOBuilder extension(String extension) {
    this.extension = extension;
    return this;
  }

  public MediaDTOBuilder contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  public MediaDTOBuilder size(Long size) {
    this.size = size;
    return this;
  }

  @Override
  public MediaDTO build() {
    MediaDTO mediaDTO = new MediaDTO();
    mediaDTO.setContentType(contentType);
    mediaDTO.setCreationDate(creationDate);
    mediaDTO.setExtension(extension);
    mediaDTO.setId(id);
    mediaDTO.setModificationDate(modificationDate);
    mediaDTO.setName(name);
    mediaDTO.setSize(size);
    mediaDTO.setSrc(src);
    return mediaDTO;
  }

  public static MediaDTOBuilder create() {
    return new MediaDTOBuilder();
  }

}
