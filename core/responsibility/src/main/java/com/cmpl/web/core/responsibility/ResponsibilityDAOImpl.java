package com.cmpl.web.core.responsibility;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Responsibility;

public class ResponsibilityDAOImpl extends BaseDAOImpl<Responsibility> implements ResponsibilityDAO {

  private final ResponsibilityRepository responsibilityRepository;

  public ResponsibilityDAOImpl(ResponsibilityRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Responsibility.class, entityRepository, publisher);
    this.responsibilityRepository = entityRepository;
  }

  @Override
  public List<Responsibility> findByUserId(String userId) {
    return responsibilityRepository.findByUserId(userId);
  }

  @Override
  public List<Responsibility> findByRoleId(String roleId) {
    return responsibilityRepository.findByRoleId(roleId);
  }

  @Override
  public Responsibility findByUserIdAndRoleId(String userId, String roleId) {
    return responsibilityRepository.findByUserIdAndRoleId(userId, roleId);
  }
}
