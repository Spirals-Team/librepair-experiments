package com.cmpl.web.core.common.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.dto.BaseDTO;

/**
 * Interface commune aux services lies aux DAO
 * 
 * @author Louis
 *
 * @param <DTO>
 */
public interface BaseService<DTO extends BaseDTO> {

  /**
   * Creer une entite
   * 
   * @param entity
   * @return
   */
  DTO createEntity(DTO entity);

  /**
   * Recuperer une entite
   * 
   * @param id
   * @return
   */
  DTO getEntity(Long id);

  /**
   * Mettre a jour une entite
   * 
   * @param entity
   * @return
   */
  DTO updateEntity(DTO entity);

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
  List<DTO> getEntities();

  /**
   * Recuperer une page d'entites
   * 
   * @param pageRequest
   * @return
   */
  Page<DTO> getPagedEntities(PageRequest pageRequest);

  /**
   * Faire une recherche
   * 
   * @param pageRequest
   * @param query
   * @return
   */
  Page<DTO> searchEntities(PageRequest pageRequest, String query);

}
