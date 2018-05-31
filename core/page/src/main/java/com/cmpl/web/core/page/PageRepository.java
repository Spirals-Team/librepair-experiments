package com.cmpl.web.core.page;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;

@Repository
public interface PageRepository extends BaseRepository<Page> {

  /**
   * Trouver une page par son nom
   * 
   * @param name
   * @return
   */
  Page findByName(String name);

}
