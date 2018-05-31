package com.cmpl.web.core.news;

import com.cmpl.web.core.common.dto.BaseDTO;

/**
 * DTO NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryDTO extends BaseDTO {

  private NewsContentDTO newsContent;
  private NewsImageDTO newsImage;
  private String facebookId;

  private String author;
  private String tags;
  private String title;
  private String name;

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public void setNewsContent(NewsContentDTO newsContent) {
    this.newsContent = newsContent;
  }

  public void setNewsImage(NewsImageDTO newsImage) {
    this.newsImage = newsImage;
  }

  public NewsContentDTO getNewsContent() {
    return newsContent;
  }

  public NewsImageDTO getNewsImage() {
    return newsImage;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAuthor() {
    return author;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
