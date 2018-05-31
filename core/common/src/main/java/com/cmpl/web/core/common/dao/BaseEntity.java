package com.cmpl.web.core.common.dao;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * Objet DAO commun
 * 
 * @author Louis
 *
 */
@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "creation_date")
  private LocalDateTime creationDate;

  @CreatedBy
  @Column(name = "creation_user")
  private String creationUser;

  @Column(name = "modification_date")
  private LocalDateTime modificationDate;

  @LastModifiedBy
  @Column(name = "modification_user")
  private String modificationUser;

  /**
   * S'assure que les elements not nullable commun sont renseignes (id, creationDate, modificationDate)
   */
  @PrePersist
  public void ensureFields() {
    if (id == null) {
      id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
    if (creationDate == null) {
      creationDate = LocalDateTime.now();
    }
    if (modificationDate == null) {
      modificationDate = LocalDateTime.now();
    }
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
