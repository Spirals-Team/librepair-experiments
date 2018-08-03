package com.cmpl.web.core.news.content;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Requete pour les NewsContent
 * 
 * @author Louis
 *
 */
public class NewsContentRequest {

  @NotBlank(message = "empty.content")
  private String content;
  private Long id;
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime creationDate;
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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
