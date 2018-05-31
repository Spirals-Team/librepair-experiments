package com.cmpl.web.core.common.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.cmpl.web.core.common.dao.BaseEntity;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.common.filler.ObjectReflexiveFillerImpl;
import com.cmpl.web.core.common.repository.BaseRepository;

/**
 * Implementation abstraire du service lie aux DAO
 * 
 * @author Louis
 *
 * @param <D>
 * @param <E>
 */
public abstract class BaseServiceImpl<D extends BaseDTO, E extends BaseEntity> implements BaseService<D> {

  private final BaseRepository<E> entityRepository;
  private final ApplicationEventPublisher publisher;

  /**
   * Constructeur a appeler via super
   * 
   * @param entityRepository
   */
  public BaseServiceImpl(BaseRepository<E> entityRepository, ApplicationEventPublisher publisher) {
    this.entityRepository = entityRepository;
    this.publisher = publisher;
  }

  @Override
  public D createEntity(D dto) {
    dto.setModificationDate(LocalDateTime.now());
    return toDTO(entityRepository.save(toEntity(dto)));

  }

  @Override
  public D getEntity(Long id) {
    Optional<E> result = entityRepository.findById(id);
    if (result == null || !result.isPresent()) {
      return null;
    }
    return toDTO(result.get());
  }

  @Override
  public D updateEntity(D dto) {
    dto.setModificationDate(LocalDateTime.now());
    return toDTO(entityRepository.save(toEntity(dto)));
  }

  @Override
  public void deleteEntity(Long id) {
    D deletedDTO = toDTO(entityRepository.getOne(id));
    entityRepository.deleteById(id);
    publisher.publishEvent(new DeletedEvent<D>(this, deletedDTO));
  }

  @Override
  public List<D> getEntities() {
    return toListDTO(entityRepository.findAll(new Sort(Direction.ASC, "creationDate")));
  }

  @Override
  public Page<D> getPagedEntities(PageRequest pageRequest) {
    return toPageDTO(entityRepository.findAll(pageRequest), pageRequest);
  }

  protected Page<D> toPageDTO(Page<E> pagedEntities, PageRequest pageRequest) {

    List<D> dtos = new ArrayList<>();

    pagedEntities.getContent().forEach(entity -> dtos.add(toDTO(entity)));

    return new PageImpl<>(dtos, pageRequest, pagedEntities.getTotalElements());
  }

  public List<D> toListDTO(List<E> entities) {
    List<D> dtos = new ArrayList<>();

    entities.forEach(entity -> dtos.add(toDTO(entity)));

    return dtos;
  }

  protected abstract D toDTO(E entity);

  protected abstract E toEntity(D dto);

  public void fillObject(Object origin, Object destination) {

    ObjectReflexiveFillerImpl reflexiveFiller = ObjectReflexiveFillerImpl.fromOriginAndDestination(origin, destination);
    reflexiveFiller.fillDestination();

  }

}
