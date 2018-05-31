package com.cmpl.web.core.style;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class StyleForm {

  private String content;
  private Long id;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime creationDate;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDateTime modificationDate;
  private String mediaName;
  private Long mediaId;
  private String creationUser;
  private String modificationUser;

  public StyleForm() {

  }

  public StyleForm(StyleDTO style) {
    this.content = style.getContent();
    this.id = style.getId();
    this.creationDate = style.getCreationDate();
    this.modificationDate = style.getModificationDate();
    this.mediaId = style.getMedia().getId();
    this.mediaName = style.getMedia().getName();
    this.creationUser = style.getCreationUser();
    this.modificationUser = style.getModificationUser();
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
  }

  public String getMediaName() {
    return mediaName;
  }

  public void setMediaName(String mediaName) {
    this.mediaName = mediaName;
  }

  public Long getMediaId() {
    return mediaId;
  }

  public void setMediaId(Long mediaId) {
    this.mediaId = mediaId;
  }

  public String getCreationUser() {
    return creationUser;
  }

  public void setCreationUser(String creationUser) {
    this.creationUser = creationUser;
  }

  public String getModificationUser() {
    return modificationUser;
  }

  public void setModificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
  }
}
