package com.cmpl.web.core.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.filler.ObjectReflexiveFillerImpl;
import com.cmpl.web.core.models.BaseEntity;

public abstract class BaseMapper<DTO extends BaseDTO, ENTITY extends BaseEntity> {

  public abstract DTO toDTO(ENTITY entity);

  public abstract ENTITY toEntity(DTO dto);

  public List<DTO> toListDTO(List<ENTITY> entities) {
    List<DTO> dtos = new ArrayList<>();

    entities.forEach(entity -> dtos.add(toDTO(entity)));

    return dtos;
  }

  public Page<DTO> toPageDTO(Page<ENTITY> pagedEntities, PageRequest pageRequest) {
    List<DTO> dtos = new ArrayList<>();

    pagedEntities.getContent().forEach(entity -> dtos.add(toDTO(entity)));

    return new PageImpl<>(dtos, pageRequest, pagedEntities.getTotalElements());
  }

  public void fillObject(Object origin, Object destination) {

    ObjectReflexiveFillerImpl reflexiveFiller = ObjectReflexiveFillerImpl.fromOriginAndDestination(origin, destination);
    reflexiveFiller.fillDestination();

  }

}
