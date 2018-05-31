package com.cmpl.web.core.role;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;

@Repository
public interface PrivilegeRepository extends BaseRepository<Privilege> {

  List<Privilege> findByRoleId(String roleId);
}
