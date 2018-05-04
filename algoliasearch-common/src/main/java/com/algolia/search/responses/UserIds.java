package com.algolia.search.responses;

import com.algolia.search.objects.UserId;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class UserIds implements Serializable {

  @JsonProperty("userIDs")
  private List<UserId> userIds;

  private Integer page;

  private Integer hitsPerPage;

  public List<UserId> getUserIds() {
    return userIds;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public UserIds setUserIds(List<UserId> userIds) {
    this.userIds = userIds;
    return this;
  }

  public UserIds setPage(Integer page) {
    this.page = page;
    return this;
  }

  public UserIds setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
    return this;
  }
}
