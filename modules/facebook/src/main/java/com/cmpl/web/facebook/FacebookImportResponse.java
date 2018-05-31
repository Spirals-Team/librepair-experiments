package com.cmpl.web.facebook;

import java.util.List;

import com.cmpl.web.core.common.resource.BaseResponse;
import com.cmpl.web.core.news.NewsEntryDTO;

/**
 * Reponse a la requete d'import de post facebook
 * 
 * @author Louis
 *
 */
public class FacebookImportResponse extends BaseResponse {

  private List<NewsEntryDTO> createdNewsEntries;

  public List<NewsEntryDTO> getCreatedNewsEntries() {
    return createdNewsEntries;
  }

  public void setCreatedNewsEntries(List<NewsEntryDTO> createdNewsEntries) {
    this.createdNewsEntries = createdNewsEntries;
  }

}
