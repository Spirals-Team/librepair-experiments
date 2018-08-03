package com.cmpl.web.core.common.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.BaseEntity;

/**
 * Implementation abstraire du service lie aux DAO
 * 
 * @author Louis
 *
 * @param <DTO>
 * @param <ENTITY>
 */
public class BaseServiceImpl<DTO extends BaseDTO, ENTITY extends BaseEntity> implements BaseService<DTO> {

  private final BaseDAO<ENTITY> dao;
  protected final BaseMapper<DTO, ENTITY> mapper;

  public BaseServiceImpl(BaseDAO<ENTITY> dao, BaseMapper<DTO, ENTITY> mapper) {

    this.dao = Objects.requireNonNull(dao);
    this.mapper = Objects.requireNonNull(mapper);
  }

  @Override
  public DTO createEntity(DTO dto) {
    return mapper.toDTO(dao.createEntity(mapper.toEntity(dto)));

  }

  @Override
  public DTO getEntity(Long id) {
    return mapper.toDTO(dao.getEntity(id));
  }

  @Override
  public DTO updateEntity(DTO dto) {
    return mapper.toDTO(dao.updateEntity(mapper.toEntity(dto)));
  }

  @Override
  public void deleteEntity(Long id) {
    dao.deleteEntity(id);
  }

  @Override
  public List<DTO> getEntities() {
    return mapper.toListDTO(dao.getEntities());
  }

  @Override
  public Page<DTO> getPagedEntities(PageRequest pageRequest) {
    return mapper.toPageDTO(dao.getPagedEntities(pageRequest), pageRequest);
  }

  @Override
  public Page<DTO> searchEntities(PageRequest pageRequest, String query) {
    return mapper.toPageDTO(dao.searchEntities(pageRequest, query), pageRequest);
  }

}
