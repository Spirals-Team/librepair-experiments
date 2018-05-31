package com.cmpl.web.core.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

/**
 * DAO NewsEntry
 * 
 * @author Louis
 *
 */
@Entity(name = "newsEntry")
@Table(name = "news_entry")
public class NewsEntry extends BaseEntity {

  @Column(name = "author")
  private String author;
  @Column(name = "tags")
  private String tags;
  @Column(name = "image_id")
  private String imageId;
  @Column(name = "content_id")
  private String contentId;
  @Column(name = "facebook_Id")
  private String facebookId;
  @Column(name = "title")
  private String title;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getImageId() {
    return imageId;
  }

  public void setImageId(String imageId) {
    this.imageId = imageId;
  }

  public String getContentId() {
    return contentId;
  }

  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFacebookId() {
    return facebookId;
  }

  public void setFacebookId(String facebookId) {
    this.facebookId = facebookId;
  }

}
