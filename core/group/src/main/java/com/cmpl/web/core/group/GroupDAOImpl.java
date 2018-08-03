package com.cmpl.web.core.group;

import com.cmpl.web.core.models.BOGroup;
import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;

public class GroupDAOImpl extends BaseDAOImpl<BOGroup> implements GroupDAO {

  public GroupDAOImpl(GroupRepository entityRepository, ApplicationEventPublisher publisher) {
    super(BOGroup.class, entityRepository, publisher);
  }
}
