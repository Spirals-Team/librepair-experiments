package com.cmpl.web.core.news;

import com.cmpl.web.core.common.resource.BaseResponse;

/**
 * Reponse NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryResponse extends BaseResponse {

  private NewsEntryDTO newsEntry;
  private String createdEntityId;

  public NewsEntryDTO getNewsEntry() {
    return newsEntry;
  }

  public void setNewsEntry(NewsEntryDTO newsEntry) {
    this.newsEntry = newsEntry;
  }

  public String getCreatedEntityId() {
    return createdEntityId;
  }

  public void setCreatedEntityId(String createdEntityId) {
    this.createdEntityId = createdEntityId;
  }
}
