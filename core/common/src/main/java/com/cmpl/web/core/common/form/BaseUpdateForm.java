package com.cmpl.web.core.common.form;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.cmpl.web.core.common.dto.BaseDTO;

public class BaseUpdateForm<D extends BaseDTO> {

  private Long id;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime creationDate;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;

  public BaseUpdateForm() {

  }

  public BaseUpdateForm(D dto) {
    this.id = dto.getId();
    this.creationDate = dto.getCreationDate();
    this.creationUser = dto.getCreationUser();
    this.modificationDate = dto.getModificationDate();
    this.modificationUser = dto.getModificationUser();
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
