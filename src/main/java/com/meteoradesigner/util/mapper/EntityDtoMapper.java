package com.meteoradesigner.util.mapper;

import com.meteoradesigner.dto.AbstractBaseDto;
import com.meteoradesigner.model.AbstractBaseEntity;

/**
 * This @code{EntityDtoMapper} interface declares behaviour or entity-dto mappers.
 */
public interface EntityDtoMapper<E extends AbstractBaseEntity, D extends AbstractBaseDto> {

    /**
     * Transforms @code{AbstractBaseEntity} instance to corresponding @code{AbstractBaseDto} one.
     *
     * @param entity to transform.
     * @return @code{AbstractBaseDto} instance.
     */
    D entityToDto(AbstractBaseEntity entity);

    /**
     * Updates @code{AbstractBaseEntity} some instance fields from corresponding
     * fields of @code{AbstractBaseDto} ones.
     *
     * @param entityToUpdate to update.
     * @param dto            source to update from.
     * @return @code{AbstractBaseEntity} instance.
     */
    E updateEntityFromDto(AbstractBaseEntity entityToUpdate,
                                           AbstractBaseDto dto);
}
