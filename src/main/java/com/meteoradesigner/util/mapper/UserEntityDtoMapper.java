package com.meteoradesigner.util.mapper;

import com.meteoradesigner.dto.AbstractBaseDto;
import com.meteoradesigner.dto.UserDto;
import com.meteoradesigner.model.AbstractBaseEntity;
import com.meteoradesigner.model.User;

/**
 * This @code{UserEntityDtoMapper} class implements @code{EntityDtoMapper} interface.
 */
public class UserEntityDtoMapper implements EntityDtoMapper<User, UserDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateEntityFromDto(AbstractBaseEntity entityToUpdate,
                                    AbstractBaseDto dto) {
        User toUpdate = (User) entityToUpdate;
        UserDto toUpdateFrom = (UserDto) dto;

        toUpdate.setId(toUpdateFrom.getId());
        toUpdate.setDisplayName(toUpdateFrom.getDisplayName());
        toUpdate.setEmail(toUpdateFrom.getEmail());
        toUpdate.setPassword(toUpdateFrom.getPassword());
        toUpdate.setRegistrationTime(toUpdateFrom.getRegistrationTime());

        return toUpdate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto entityToDto(AbstractBaseEntity entity) {

        User toTransform = (User) entity;
        return new UserDto(
                toTransform.getId(),
                toTransform.getDisplayName(),
                toTransform.getEmail(),
                toTransform.getPassword(),
                toTransform.getRegistrationTime()
        );
    }
}