package com.cmpl.web.facebook;

import java.util.List;

import com.cmpl.web.core.news.NewsEntryDTO;

/**
 * Translator pour les request d'import de post facebook
 * 
 * @author Louis
 *
 */
public interface FacebookImportTranslator {

  /**
   * Transforme les request REST en objet pour l'import
   * 
   * @param request
   * @return
   */
  List<FacebookImportPost> fromRequestToPosts(FacebookImportRequest request);

  /**
   * Transforme les objets importes en reponse
   * 
   * @param dtos
   * @return
   */
  FacebookImportResponse fromDTOToResponse(List<NewsEntryDTO> dtos);

}
