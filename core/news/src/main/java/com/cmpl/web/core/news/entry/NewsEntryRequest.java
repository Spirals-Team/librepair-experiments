package com.cmpl.web.core.news.entry;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

import com.cmpl.web.core.news.content.NewsContentRequest;
import com.cmpl.web.core.news.image.NewsImageRequest;

/**
 * Requete NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryRequest {

  @NotBlank(message = "empty.author")
  private String author;
  private Long id;
  @NotBlank(message = "empty.title")
  private String title;
  private String tags;
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime creationDate;
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;

  @Valid
  @Nullable
  private NewsContentRequest content;
  @Valid
  @Nullable
  private NewsImageRequest image;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public NewsContentRequest getContent() {
    return content;
  }

  public void setContent(NewsContentRequest content) {
    this.content = content;
  }

  public NewsImageRequest getImage() {
    return image;
  }

  public void setImage(NewsImageRequest image) {
    this.image = image;
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
