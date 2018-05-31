package com.cmpl.web.core.news;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class NewsEntryRequestBuilder extends Builder<NewsEntryRequest> {

  private String author;
  private String tags;
  private String title;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;

  private NewsContentRequest content;
  private NewsImageRequest image;

  private NewsEntryRequestBuilder() {

  }

  public NewsEntryRequestBuilder author(String author) {
    this.author = author;
    return this;
  }

  public NewsEntryRequestBuilder tags(String tags) {
    this.tags = tags;
    return this;
  }

  public NewsEntryRequestBuilder title(String title) {
    this.title = title;
    return this;
  }

  public NewsEntryRequestBuilder content(NewsContentRequest content) {
    this.content = content;
    return this;
  }

  public NewsEntryRequestBuilder image(NewsImageRequest image) {
    this.image = image;
    return this;
  }

  public NewsEntryRequestBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public NewsEntryRequestBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public NewsEntryRequestBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public NewsEntryRequestBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public NewsEntryRequestBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public NewsEntryRequest build() {
    NewsEntryRequest request = new NewsEntryRequest();

    request.setId(id);
    request.setContent(content);
    request.setCreationDate(creationDate);
    request.setModificationDate(modificationDate);
    request.setAuthor(author);
    request.setTags(tags);
    request.setTitle(title);
    request.setImage(image);
    request.setCreationUser(creationUser);
    request.setModificationUser(modificationUser);
    return request;
  }

  public static NewsEntryRequestBuilder create() {
    return new NewsEntryRequestBuilder();
  }

}
