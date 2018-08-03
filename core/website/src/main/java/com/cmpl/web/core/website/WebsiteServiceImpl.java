package com.cmpl.web.core.website;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Website;

public class WebsiteServiceImpl extends BaseServiceImpl<WebsiteDTO, Website> implements WebsiteService {

  private final WebsiteDAO websiteDAO;

  public WebsiteServiceImpl(WebsiteDAO dao, WebsiteMapper mapper) {
    super(dao, mapper);
    this.websiteDAO = dao;
  }

  @Override
  public WebsiteDTO getWebsiteByName(String websiteName) {
    return mapper.toDTO(websiteDAO.getWebsiteByName(websiteName));
  }
}
