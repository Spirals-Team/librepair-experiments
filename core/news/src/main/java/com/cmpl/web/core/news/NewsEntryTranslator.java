package com.cmpl.web.core.news;


/**
 * Translator pour les requetes de creation/modification de NewsEntry
 * 
 * @author Louis
 *
 */
public interface NewsEntryTranslator {

  /**
   * Transforme les request REST en objet pour l'import
   * 
   * @param request
   * @return
   */
  NewsEntryDTO fromRequestToDTO(NewsEntryRequest request);

  /**
   * Transforme les objets importes en reponse
   * 
   * @param dto
   * @return
   */
  NewsEntryResponse fromDTOToResponse(NewsEntryDTO dto);
}
