package com.cmpl.web.core.style;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "style")
@Table(name = "style")
public class Style extends BaseEntity {

  @Column(name = "media_id")
  private String mediaId;

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
