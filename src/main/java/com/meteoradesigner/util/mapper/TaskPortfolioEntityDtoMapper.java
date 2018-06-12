package com.meteoradesigner.util.mapper;

import com.meteoradesigner.dto.AbstractBaseDto;
import com.meteoradesigner.dto.TaskPortfolioDto;
import com.meteoradesigner.model.AbstractBaseEntity;
import com.meteoradesigner.model.TaskPortfolio;

/**
 * This @code{TaskPortfolioEntityDtoMapper} class implements @code{EntityDtoMapper} interface.
 */
public class TaskPortfolioEntityDtoMapper
        implements EntityDtoMapper<TaskPortfolio, TaskPortfolioDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskPortfolio updateEntityFromDto(AbstractBaseEntity entityToUpdate,
                                             AbstractBaseDto dto) {
        TaskPortfolio toUpdate = (TaskPortfolio) entityToUpdate;
        TaskPortfolioDto toUpdateFrom = (TaskPortfolioDto) dto;

        toUpdate.setId(toUpdateFrom.getId());
        toUpdate.setDisplayName(toUpdateFrom.getDisplayName());
        toUpdate.setDescription(toUpdateFrom.getDescription());

        return toUpdate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskPortfolioDto entityToDto(AbstractBaseEntity entity) {

        TaskPortfolio toTransform = (TaskPortfolio) entity;
        return new TaskPortfolioDto(
                toTransform.getId(),
                toTransform.getDisplayName(),
                toTransform.getUser().getId(),
                toTransform.getDescription()
        );
    }

}