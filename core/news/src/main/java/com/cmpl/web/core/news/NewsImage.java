package com.cmpl.web.core.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

/**
 * DAO NewsImage
 * 
 * @author Louis
 *
 */
@Entity(name = "newsImage")
@Table(name = "news_image")
public class NewsImage extends BaseEntity {

  @Column(name = "mediaId")
  private String mediaId;
  @Column(name = "legend")
  private String legend;
  @Column(name = "alt")
  private String alt;

  public String getLegend() {
    return legend;
  }

  public void setLegend(String legend) {
    this.legend = legend;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
