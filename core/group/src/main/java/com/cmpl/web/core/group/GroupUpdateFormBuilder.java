package com.cmpl.web.core.group;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class GroupUpdateFormBuilder extends Builder<GroupUpdateForm> {

  private String name;
  private String description;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;

  private GroupUpdateFormBuilder() {

  }

  public GroupUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public GroupUpdateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  public GroupUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public GroupUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public GroupUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public GroupUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public GroupUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public GroupUpdateForm build() {
    GroupUpdateForm form = new GroupUpdateForm();
    form.setDescription(description);
    form.setName(name);
    form.setId(id);
    form.setCreationDate(creationDate);
    form.setModificationDate(modificationDate);
    form.setCreationUser(creationUser);
    form.setModificationUser(modificationUser);
    return form;
  }

  public static GroupUpdateFormBuilder create() {
    return new GroupUpdateFormBuilder();
  }

}
