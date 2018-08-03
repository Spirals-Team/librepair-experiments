package com.cmpl.web.core.common.builder;

import java.time.LocalDateTime;

public abstract class BaseBuilder<T> extends Builder<T> {

  protected Long id;
  protected LocalDateTime creationDate;
  protected LocalDateTime modificationDate;
  protected String creationUser;
  protected String modificationUser;

  public BaseBuilder<T> id(Long id) {
    this.id = id;
    return this;
  }

  public BaseBuilder<T> creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public BaseBuilder<T> creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public BaseBuilder<T> modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  public BaseBuilder<T> modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

}
