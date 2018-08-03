package com.cmpl.web.core.website;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Website;

public class WebsiteDAOImpl extends BaseDAOImpl<Website> implements WebsiteDAO {

  private final WebsiteRepository websiteRepository;

  public WebsiteDAOImpl(WebsiteRepository websiteRepository, ApplicationEventPublisher publisher) {
    super(Website.class, websiteRepository, publisher);
    this.websiteRepository = websiteRepository;
  }

  @Override
  public Website getWebsiteByName(String websiteName) {
    return websiteRepository.findByName(websiteName);
  }
}
