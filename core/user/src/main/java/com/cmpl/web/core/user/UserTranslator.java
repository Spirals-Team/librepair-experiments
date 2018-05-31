package com.cmpl.web.core.user;

public interface UserTranslator {

  UserDTO fromCreateFormToDTO(UserCreateForm form);

  UserDTO fromUpdateFormToDTO(UserUpdateForm form);

  UserResponse fromDTOToResponse(UserDTO dto);

}
