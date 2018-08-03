package com.cmpl.web.core.group;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.BOGroup;

public class GroupServiceImpl extends BaseServiceImpl<GroupDTO, BOGroup> implements GroupService {

  public GroupServiceImpl(GroupDAO groupDAO, GroupMapper groupMapper) {
    super(groupDAO, groupMapper);
  }

}
