package com.algolia.search.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserId {

  @JsonProperty("userID")
  private String userId;

  private String clusterName;

  private Long nbRecords;

  private Long dataSize;

  public String getUserId() {
    return userId;
  }

  public String getClusterName() {
    return clusterName;
  }

  public Long getNbRecords() {
    return nbRecords;
  }

  public Long getDataSize() {
    return dataSize;
  }

  public UserId setUserId(String id) {
    this.userId = id;
    return this;
  }

  public UserId setClusterName(String clusterName) {
    this.clusterName = clusterName;
    return this;
  }

  public UserId setNbRecords(Long nbRecords) {
    this.nbRecords = nbRecords;
    return this;
  }

  public UserId setDataSize(Long dataSize) {
    this.dataSize = dataSize;
    return this;
  }

  public String toString() {
    return "UserId{"
        + "userId='"
        + userId
        + '\''
        + ", clusterName='"
        + clusterName
        + '\''
        + ", nbRecords="
        + nbRecords
        + '\''
        + ", dataSize="
        + dataSize
        + '}';
  }
}
