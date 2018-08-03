package com.cmpl.web.core.user;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class UserUpdateFormBuilder extends Builder<UserUpdateForm> {

  private String login;
  private LocalDateTime lastConnection;
  private String email;
  private String description;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;
  private LocalDateTime lastPasswordModification;

  private UserUpdateFormBuilder() {

  }

  public UserUpdateFormBuilder login(String login) {
    this.login = login;
    return this;
  }

  public UserUpdateFormBuilder lastConnection(LocalDateTime lastConnection) {
    this.lastConnection = lastConnection;
    return this;
  }

  public UserUpdateFormBuilder lastPasswordModification(LocalDateTime lastPasswordModification) {
    this.lastConnection = lastPasswordModification;
    return this;
  }

  public UserUpdateFormBuilder email(String email) {
    this.email = email;
    return this;
  }

  public UserUpdateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  public UserUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public UserUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public UserUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public UserUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public UserUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public UserUpdateForm build() {
    UserUpdateForm form = new UserUpdateForm();
    form.setDescription(description);
    form.setEmail(email);
    form.setLastConnection(lastConnection);
    form.setLogin(login);
    form.setCreationDate(creationDate);
    form.setCreationUser(creationUser);
    form.setModificationDate(modificationDate);
    form.setModificationUser(modificationUser);
    form.setId(id);

    return form;
  }

  public static UserUpdateFormBuilder create() {
    return new UserUpdateFormBuilder();
  }
}
