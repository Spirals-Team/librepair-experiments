package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.resource.BaseResponse;

public class AssociationUserRoleResponse extends BaseResponse {

  private AssociationUserRoleDTO associationUserRoleDTO;

  public AssociationUserRoleDTO getAssociationUserRoleDTO() {
    return associationUserRoleDTO;
  }

  public void setAssociationUserRoleDTO(AssociationUserRoleDTO associationUserRoleDTO) {
    this.associationUserRoleDTO = associationUserRoleDTO;
  }
}
