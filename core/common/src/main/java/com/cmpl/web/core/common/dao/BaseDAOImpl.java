package com.cmpl.web.core.common.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.common.user.GroupGrantedAuthority;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.QBaseEntity;
import com.cmpl.web.core.models.QMembership;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

public class BaseDAOImpl<ENTITY extends BaseEntity> extends QuerydslRepositorySupport implements BaseDAO<ENTITY> {

  private final BaseRepository<ENTITY> entityRepository;
  private final ApplicationEventPublisher publisher;
  private final Class<ENTITY> entityClass;

  public BaseDAOImpl(Class<ENTITY> domainClass, BaseRepository<ENTITY> entityRepository,
      ApplicationEventPublisher publisher) {
    super(domainClass);

    this.entityClass = domainClass;
    this.entityRepository = Objects.requireNonNull(entityRepository);
    this.publisher = Objects.requireNonNull(publisher);
  }

  @Override
  public ENTITY createEntity(ENTITY entity) {
    entity.setModificationDate(LocalDateTime.now());
    return entityRepository.save(entity);
  }

  @Override
  public ENTITY getEntity(Long id) {
    Optional<ENTITY> result = entityRepository.findById(id);
    if (result == null || !result.isPresent()) {
      return null;
    }
    return result.get();
  }

  @Override
  public ENTITY updateEntity(ENTITY entity) {
    entity.setModificationDate(LocalDateTime.now());
    return entityRepository.save(entity);
  }

  @Override
  public void deleteEntity(Long id) {
    ENTITY deletedEntity = entityRepository.getOne(id);
    publisher.publishEvent(new DeletedEvent<ENTITY>(this, deletedEntity));
    entityRepository.delete(deletedEntity);
  }

  @Override
  public List<ENTITY> getEntities() {
    return Lists.newArrayList(entityRepository.findAll(getSecuredPredicate(), new Sort(Direction.ASC, "creationDate")));
  }

  @Override
  public Page<ENTITY> getPagedEntities(PageRequest pageRequest) {
    return entityRepository.findAll(getSecuredPredicate(), pageRequest);
  }

  private Predicate getSecuredPredicate() {
    return getAllPredicate(entityClass);
  }

  private Predicate getAllPredicate(Class entityClass) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      return getDefaultAllPredicate(entityClass, auth, getGroupIds(auth.getAuthorities()));
    }
    return null;
  }

  private List<Long> getGroupIds(Collection<? extends GrantedAuthority> authorities) {
    List<Long> groupIds = new ArrayList<>();
    if (authorities != null) {
      for (GrantedAuthority grantedAuthority : authorities) {
        if (grantedAuthority instanceof GroupGrantedAuthority) {
          GroupGrantedAuthority groupGrantedAuthority = (GroupGrantedAuthority) grantedAuthority;
          groupIds.add(groupGrantedAuthority.getGroupId());
        }
      }
    }
    return groupIds;
  }

  private Predicate getDefaultAllPredicate(Class entityClass, Authentication auth, List<Long> groupIds) {
    QMembership subQ = QMembership.membership;

    String entityPathName = entityClass.getSimpleName().substring(0, 1).toLowerCase()
        + entityClass.getSimpleName().substring(1);

    Path<? extends BaseEntity> entityPath = Expressions.path(entityClass, entityPathName);

    QBaseEntity boEntityPath = new QBaseEntity(entityPath);
    return boEntityPath.creationUser.eq(auth.getName())
        .or(boEntityPath.id
            .in(new JPAQuery<>().from(subQ).select(subQ.entityId).where(subQ.groupId.in(groupIds)).distinct()))
        .or(new JPAQuery<>().from(subQ).select(subQ.id).where(subQ.entityId.eq(boEntityPath.id)).isNull());
  }

  @Override
  public Page<ENTITY> searchEntities(PageRequest pageRequest, String query) {
    return null;
  }
}
