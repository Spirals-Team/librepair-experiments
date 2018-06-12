package com.meteoradesigner.util.mapper;

import com.meteoradesigner.dto.AbstractBaseDto;
import com.meteoradesigner.dto.TaskDto;
import com.meteoradesigner.model.AbstractBaseEntity;
import com.meteoradesigner.model.Task;

/**
 * This @code{TaskEntityDtoMapper} class implements @code{EntityDtoMapper} interface.
 */
public class TaskEntityDtoMapper implements EntityDtoMapper<Task, TaskDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Task updateEntityFromDto(AbstractBaseEntity entityToUpdate,
                                    AbstractBaseDto dto) {
        Task toUpdate = (Task) entityToUpdate;
        TaskDto toUpdateFrom = (TaskDto) dto;

        toUpdate.setId(toUpdateFrom.getId());
        toUpdate.setDisplayName(toUpdateFrom.getDisplayName());
        toUpdate.setDescription(toUpdateFrom.getDescription());
        toUpdate.setPlannedStartTaskTimestamp(toUpdateFrom.getPlannedStartTaskTimestamp());
        toUpdate.setPlannedStopTaskTimestamp(toUpdateFrom.getPlannedStopTaskTimestamp());
        toUpdate.setActualStartTaskTimestamp(toUpdateFrom.getActualStartTaskTimestamp());
        toUpdate.setActualStopTaskTimestamp(toUpdateFrom.getActualStopTaskTimestamp());
        toUpdate.setMetrics(toUpdateFrom.getMetrics());

        return toUpdate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDto entityToDto(AbstractBaseEntity entity) {

        Task toTransform = (Task) entity;
        return new TaskDto(
                toTransform.getId(),
                toTransform.getDisplayName(),
                toTransform.getUser().getId(),
                toTransform.getDescription(),
                toTransform.getPlannedStartTaskTimestamp(),
                toTransform.getPlannedStopTaskTimestamp(),
                toTransform.getActualStartTaskTimestamp(),
                toTransform.getActualStopTaskTimestamp(),
                toTransform.getPortfolio().getId(),
                toTransform.getMetrics()
        );
    }
}