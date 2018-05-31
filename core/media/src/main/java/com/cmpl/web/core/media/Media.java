package com.cmpl.web.core.media;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity
@Table(name = "media")
public class Media extends BaseEntity {

  @Column(name = "src", nullable = false)
  private String src;
  @Column(name = "name", nullable = false, unique = true)
  private String name;
  @Column(name = "extension", nullable = false)
  private String extension;
  @Column(name = "contentType", nullable = false)
  private String contentType;
  @Column(name = "size", nullable = false)
  private Long size;

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

}
