package com.cmpl.web.facebook;

import java.util.List;

import com.cmpl.web.core.news.NewsEntryDTO;

/**
 * Implementation du Translator pour les request d'import de post facebook
 * 
 * @author Louis
 *
 */
public class FacebookImportTranslatorImpl implements FacebookImportTranslator {

  @Override
  public List<FacebookImportPost> fromRequestToPosts(FacebookImportRequest request) {
    return request.getPostsToImport();
  }

  @Override
  public FacebookImportResponse fromDTOToResponse(List<NewsEntryDTO> dtos) {
    FacebookImportResponse response = new FacebookImportResponse();
    response.setCreatedNewsEntries(dtos);
    return response;
  }

}
