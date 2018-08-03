package com.cmpl.web.core.news.image;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.NewsImage;

public class NewsImageDAOImpl extends BaseDAOImpl<NewsImage> implements NewsImageDAO {

  public NewsImageDAOImpl(NewsImageRepository entityRepository, ApplicationEventPublisher publisher) {
    super(NewsImage.class, entityRepository, publisher);
  }
}
