package com.cmpl.web.facebook;

import java.time.LocalDate;

import org.springframework.social.facebook.api.Post.PostType;

/**
 * Objet representant un post facebook importable en tant que NewsEntry
 * 
 * @author Louis
 *
 */
public class ImportablePost {

  private String author;
  private String title;
  private PostType type;
  private String description;
  private String photoUrl;
  private String videoUrl;
  private String linkUrl;
  private String facebookId;
  private LocalDate creationDate;
  private String formattedDate;
  private String objectId;
  private String onclick;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PostType getType() {
    return type;
  }

  public void setType(PostType type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

  public boolean isPhoto() {
    return PostType.PHOTO.equals(this.type);
  }

  public boolean isStatus() {
    return PostType.STATUS.equals(this.type);
  }

  public boolean isLink() {
    return PostType.LINK.equals(this.type);
  }

  public boolean isVideo() {
    return PostType.VIDEO.equals(this.type);
  }

  public String getFacebookId() {
    return facebookId;
  }

  public void setFacebookId(String facebookId) {
    this.facebookId = facebookId;
  }

  public String getOnclick() {
    return onclick;
  }

  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public String getFormattedDate() {
    return formattedDate;
  }

  public void setFormattedDate(String formattedDate) {
    this.formattedDate = formattedDate;
  }

  public String getObjectId() {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

}
