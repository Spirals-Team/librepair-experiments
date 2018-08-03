package com.cmpl.web.core.common.dto;

import java.time.LocalDateTime;

/**
 * DTO commun
 * 
 * @author Louis
 *
 */
public abstract class BaseDTO {

  private Long id;
  private LocalDateTime creationDate;
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

  public String getModificationUser() {
    return modificationUser;
  }

  public void setModificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
  }

  public String getCreationUser() {

    return creationUser;
  }

  public void setCreationUser(String creationUser) {
    this.creationUser = creationUser;
  }
}
