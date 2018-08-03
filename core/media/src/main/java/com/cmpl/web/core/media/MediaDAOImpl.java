package com.cmpl.web.core.media;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Media;

public class MediaDAOImpl extends BaseDAOImpl<Media> implements MediaDAO {

  private final MediaRepository mediaRepository;

  public MediaDAOImpl(MediaRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Media.class, entityRepository, publisher);
    this.mediaRepository = entityRepository;
  }

  @Override
  public Media findByName(String name) {
    return mediaRepository.findByName(name);
  }
}
