package com.cmpl.web.core.news;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.media.MediaDTO;

public class NewsImageRequestBuilder extends Builder<NewsImageRequest> {

  private String legend;
  private String alt;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;
  private MediaDTO media;

  private NewsImageRequestBuilder() {

  }

  public NewsImageRequestBuilder legend(String legend) {
    this.legend = legend;
    return this;
  }

  public NewsImageRequestBuilder alt(String alt) {
    this.alt = alt;
    return this;
  }

  public NewsImageRequestBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsImageRequestBuilder media(MediaDTO media) {
    this.media = media;
    return this;
  }

  public NewsImageRequestBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsImageRequestBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsImageRequestBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public NewsImageRequestBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public NewsImageRequest build() {
    NewsImageRequest imageRequest = new NewsImageRequest();

    imageRequest.setAlt(alt);
    imageRequest.setId(id);
    imageRequest.setCreationDate(creationDate);
    imageRequest.setModificationDate(modificationDate);
    imageRequest.setLegend(legend);
    imageRequest.setMedia(media);
    imageRequest.setCreationUser(creationUser);
    imageRequest.setModificationUser(modificationUser);

    return imageRequest;
  }

  public static NewsImageRequestBuilder create() {
    return new NewsImageRequestBuilder();
  }

}
