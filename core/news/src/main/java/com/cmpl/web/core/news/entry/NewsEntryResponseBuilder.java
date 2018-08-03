package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.builder.Builder;

public class NewsEntryResponseBuilder extends Builder<NewsEntryResponse> {

  private NewsEntryDTO newsEntry;
  private String createdEntityId;

  private NewsEntryResponseBuilder() {

  }

  public NewsEntryResponseBuilder newsEntry(NewsEntryDTO newsEntry) {
    this.newsEntry = newsEntry;
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

    return response;
  }

  public static NewsEntryResponseBuilder create() {
    return new NewsEntryResponseBuilder();
  }
}
