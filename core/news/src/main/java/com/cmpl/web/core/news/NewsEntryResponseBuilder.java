package com.cmpl.web.core.news;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class NewsEntryResponseBuilder extends Builder<NewsEntryResponse> {

  private NewsEntryDTO newsEntry;
  private String createdEntityId;
  private Error error;

  private NewsEntryResponseBuilder() {

  }

  public NewsEntryResponseBuilder newsEntry(NewsEntryDTO newsEntry) {
    this.newsEntry = newsEntry;
    return this;
  }

  public NewsEntryResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  public NewsEntryResponseBuilder createdEntityId(String createdEntityId) {
    this.createdEntityId = createdEntityId;
    return this;
  }

  @Override
  public NewsEntryResponse build() {
    NewsEntryResponse response = new NewsEntryResponse();
    response.setCreatedEntityId(createdEntityId);
    response.setNewsEntry(newsEntry);
    response.setError(error);
    return response;
  }

  public static NewsEntryResponseBuilder create() {
    return new NewsEntryResponseBuilder();
  }
}
