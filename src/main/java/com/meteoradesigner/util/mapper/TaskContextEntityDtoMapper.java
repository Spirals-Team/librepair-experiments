package com.meteoradesigner.util.mapper;

import com.meteoradesigner.dto.AbstractBaseDto;
import com.meteoradesigner.dto.TaskContextDto;
import com.meteoradesigner.model.AbstractBaseEntity;
import com.meteoradesigner.model.TaskContext;

/**
 * This @code{TaskContextEntityDtoMapper} class implements @code{EntityDtoMapper} interface.
 */
public class TaskContextEntityDtoMapper implements EntityDtoMapper<TaskContext, TaskContextDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskContext updateEntityFromDto(AbstractBaseEntity entityToUpdate,
                                           AbstractBaseDto dto) {
        TaskContext toUpdate = (TaskContext) entityToUpdate;
        TaskContextDto toUpdateFrom = (TaskContextDto) dto;

        toUpdate.setId(toUpdateFrom.getId());
        toUpdate.setDisplayName(toUpdateFrom.getDisplayName());
        toUpdate.setDescription(toUpdateFrom.getDescription());

        return toUpdate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskContextDto entityToDto(AbstractBaseEntity entity) {

        TaskContext toTransform = (TaskContext) entity;
        return new TaskContextDto(
                toTransform.getId(),
                toTransform.getDisplayName(),
                toTransform.getUser().getId(),
                toTransform.getDescription()
        );
    }
}