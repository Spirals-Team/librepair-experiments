package com.cmpl.web.core.role.privilege;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Privilege;

@Repository
public interface PrivilegeRepository extends BaseRepository<Privilege> {

  List<Privilege> findByRoleId(String roleId);
}
