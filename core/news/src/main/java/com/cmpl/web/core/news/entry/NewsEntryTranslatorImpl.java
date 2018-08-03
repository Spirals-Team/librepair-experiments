package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.news.content.NewsContentDTO;
import com.cmpl.web.core.news.content.NewsContentDTOBuilder;
import com.cmpl.web.core.news.content.NewsContentRequest;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageDTOBuilder;
import com.cmpl.web.core.news.image.NewsImageRequest;

/**
 * Implementation du Translator pour les requetes de creation/modification de NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryTranslatorImpl implements NewsEntryTranslator {

  @Override
  public NewsEntryDTO fromRequestToDTO(NewsEntryRequest request) {
    return NewsEntryDTOBuilder.create().author(request.getAuthor()).tags(request.getTags()).title(request.getTitle())
        .newsContent(fromRequestToDTO(request.getContent())).newsImage(fromRequestToDTO(request.getImage()))
        .id(request.getId()).creationDate(request.getCreationDate()).modificationDate(request.getModificationDate())
        .build();
  }

  @Override
  public NewsContentDTO fromRequestToDTO(NewsContentRequest request) {
    if (request == null) {
      return null;
    }
    return NewsContentDTOBuilder.create().content(request.getContent()).creationDate(request.getCreationDate())
        .id(request.getId()).modificationDate(request.getModificationDate()).build();
  }

  @Override
  public NewsImageDTO fromRequestToDTO(NewsImageRequest request) {
    if (request == null) {
      return null;
    }
    return NewsImageDTOBuilder.create().alt(request.getAlt()).legend(request.getLegend())
        .creationDate(request.getCreationDate()).id(request.getId()).modificationDate(request.getModificationDate())
        .build();
  }

  @Override
  public NewsEntryResponse fromDTOToResponse(NewsEntryDTO dto) {
    return NewsEntryResponseBuilder.create().newsEntry(dto).createdEntityId(String.valueOf(dto.getId())).build();

  }

}
