package com.cmpl.web.core.news;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;

/**
 * Repository des NewsEntry
 * 
 * @author Louis
 *
 */
@Repository
public interface NewsEntryRepository extends BaseRepository<NewsEntry> {

  /**
   * Recupere les entree dont les dates de creation sont entre le start et le end
   * 
   * @param start
   * @param end
   * @return
   */
  List<NewsEntry> findByCreationDateBetween(Date start, Date end);

  /**
   * Recupere les entree dont l'id facebook correspond a facebookId
   * 
   * @param facebookId
   * @return
   */
  List<NewsEntry> findByFacebookId(String facebookId);

}
