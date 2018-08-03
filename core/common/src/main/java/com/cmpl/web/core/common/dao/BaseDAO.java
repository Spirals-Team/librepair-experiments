package com.cmpl.web.core.common.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.models.BaseEntity;

public interface BaseDAO<ENTITY extends BaseEntity> {

  /**
   * Creer une entite
   *
   * @param entity
   * @return
   */
  ENTITY createEntity(ENTITY entity);

  /**
   * Recuperer une entite
   *
   * @param id
   * @return
   */
  ENTITY getEntity(Long id);

  /**
   * Mettre a jour une entite
   *
   * @param entity
   * @return
   */
  ENTITY updateEntity(ENTITY entity);

  /**
   * Supprimer une entite
   *
   * @param id
   */
  void deleteEntity(Long id);

  /**
   * Recuperer toutes les entites
   *
   * @return
   */
  List<ENTITY> getEntities();

  /**
   * Recuperer une page d'entites
   *
   * @param pageRequest
   * @return
   */
  Page<ENTITY> getPagedEntities(PageRequest pageRequest);

  /**
   * Faire une recherche
   *
   * @param pageRequest
   * @param query
   * @return
   */
  Page<ENTITY> searchEntities(PageRequest pageRequest, String query);

}
