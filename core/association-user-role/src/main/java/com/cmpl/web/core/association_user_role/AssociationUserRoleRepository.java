package com.cmpl.web.core.association_user_role;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;

@Repository
public interface AssociationUserRoleRepository extends BaseRepository<AssociationUserRole> {

  List<AssociationUserRole> findByUserId(String userId);

  List<AssociationUserRole> findByRoleId(String roleId);

  AssociationUserRole findByUserIdAndRoleId(String userId, String roleId);

}
