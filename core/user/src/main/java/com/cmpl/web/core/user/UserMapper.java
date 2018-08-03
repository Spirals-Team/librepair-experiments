package com.cmpl.web.core.user;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.User;

public class UserMapper extends BaseMapper<UserDTO, User> {

  @Override
  public UserDTO toDTO(User entity) {
    UserDTO dto = UserDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public User toEntity(UserDTO dto) {
    User entity = UserBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
