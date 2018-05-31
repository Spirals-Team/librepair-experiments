package com.cmpl.web.core.role;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class RoleUpdateFormBuilder extends Builder<RoleUpdateForm> {

  private String name;
  private String description;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;

  private RoleUpdateFormBuilder() {

  }

  public RoleUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public RoleUpdateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  public RoleUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public RoleUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public RoleUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public RoleUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public RoleUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public RoleUpdateForm build() {
    RoleUpdateForm form = new RoleUpdateForm();
    form.setDescription(description);
    form.setName(name);
    form.setId(id);
    form.setCreationDate(creationDate);
    form.setModificationDate(modificationDate);
    form.setCreationUser(creationUser);
    form.setModificationUser(modificationUser);
    return form;
  }

  public static RoleUpdateFormBuilder create() {
    return new RoleUpdateFormBuilder();
  }
}
