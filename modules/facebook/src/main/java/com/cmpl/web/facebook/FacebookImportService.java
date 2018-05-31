package com.cmpl.web.facebook;

import java.util.List;
import java.util.Locale;

import com.cmpl.web.core.news.NewsEntryDTO;

/**
 * Interface d'import de posts facebook en NewsEntry
 * 
 * @author Louis
 *
 */
public interface FacebookImportService {

  /**
   * Importer des posts facebook en NewsEntry et les remonter
   * 
   * @param facebookPosts
   * @param locale
   * @return
   */
  List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts, Locale locale);

}
