package com.les.brouilles.planner.service.mapper;

public interface Mapper<TEntity, TDto> {

	TEntity convertToEntity(TDto dto);

	TDto convertToDTO(TEntity entity);

}
