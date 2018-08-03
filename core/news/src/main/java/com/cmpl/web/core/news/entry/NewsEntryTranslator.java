package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.news.content.NewsContentDTO;
import com.cmpl.web.core.news.content.NewsContentRequest;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageRequest;

/**
 * Translator pour les requetes de creation/modification de NewsEntry
 * 
 * @author Louis
 *
 */
public interface NewsEntryTranslator {

  NewsEntryDTO fromRequestToDTO(NewsEntryRequest request);

  NewsContentDTO fromRequestToDTO(NewsContentRequest request);

  NewsImageDTO fromRequestToDTO(NewsImageRequest request);

  NewsEntryResponse fromDTOToResponse(NewsEntryDTO dto);
}
