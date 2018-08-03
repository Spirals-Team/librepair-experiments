package com.cmpl.web.core.news.entry;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.NewsEntry;

/**
 * Repository des NewsEntry
 * 
 * @author Louis
 *
 */
@Repository
public interface NewsEntryRepository extends BaseRepository<NewsEntry> {

  /**
   * Recupere les entree dont l'id facebook correspond a facebookId
   * 
   * @param facebookId
   * @return
   */
  List<NewsEntry> findByFacebookId(String facebookId);

}
