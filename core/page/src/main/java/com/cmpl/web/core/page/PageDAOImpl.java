package com.cmpl.web.core.page;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Page;

public class PageDAOImpl extends BaseDAOImpl<Page> implements PageDAO {

  private final PageRepository pageRepository;

  public PageDAOImpl(PageRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Page.class, entityRepository, publisher);
    this.pageRepository = entityRepository;
  }

  @Override
  public Page getPageByName(String pageName) {
    return pageRepository.findByName(pageName);
  }

  @Override
  public List<Page> getPages(Sort sort) {
    return pageRepository.findAll(sort);
  }
}
