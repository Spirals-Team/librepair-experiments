package com.cmpl.web.core.user;

public class UserTranslatorImpl implements UserTranslator {

  @Override
  public UserDTO fromCreateFormToDTO(UserCreateForm form) {
    return UserDTOBuilder.create().description(form.getDescription()).login(form.getLogin()).email(form.getEmail())
        .build();
  }

  @Override
  public UserDTO fromUpdateFormToDTO(UserUpdateForm form) {
    return UserDTOBuilder.create().email(form.getEmail()).login(form.getLogin()).description(form.getDescription())
        .lastConnection(form.getLastConnection()).lastPasswordModification(form.getLastPasswordModification())
        .creationDate(form.getCreationDate()).modificationDate(form.getModificationDate())
        .creationUser(form.getCreationUser()).modificationUser(form.getModificationUser()).id(form.getId()).build();
  }

  @Override
  public UserResponse fromDTOToResponse(UserDTO dto) {
    return UserResponseBuilder.create().user(dto).build();
  }
}
