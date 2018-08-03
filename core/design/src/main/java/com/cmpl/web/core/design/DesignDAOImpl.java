package com.cmpl.web.core.design;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Design;

public class DesignDAOImpl extends BaseDAOImpl<Design> implements DesignDAO {

  private final DesignRepository designRepository;

  public DesignDAOImpl(DesignRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Design.class, entityRepository, publisher);
    this.designRepository = entityRepository;
  }

  @Override
  public List<Design> findByWebsiteId(Long websiteId) {
    return designRepository.findByWebsiteId(websiteId);
  }

  @Override
  public List<Design> findByStyleId(Long styleId) {
    return designRepository.findByStyleId(styleId);
  }

  @Override
  public Design findByWebsiteIdAndStyleId(Long websiteId, Long styleId) {
    return designRepository.findByWebsiteIdAndStyleId(websiteId, styleId);
  }
}
