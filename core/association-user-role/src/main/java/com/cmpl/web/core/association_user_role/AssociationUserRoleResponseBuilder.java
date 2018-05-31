package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class AssociationUserRoleResponseBuilder extends Builder<AssociationUserRoleResponse> {

  private AssociationUserRoleDTO associationUserRoleDTO;
  private Error error;

  public AssociationUserRoleResponseBuilder associationUserRoleDTO(AssociationUserRoleDTO associationUserRoleDTO) {
    this.associationUserRoleDTO = associationUserRoleDTO;
    return this;
  }

  public AssociationUserRoleResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  private AssociationUserRoleResponseBuilder() {

  }

  @Override
  public AssociationUserRoleResponse build() {
    AssociationUserRoleResponse associationUserRoleResponse = new AssociationUserRoleResponse();
    associationUserRoleResponse.setAssociationUserRoleDTO(associationUserRoleDTO);
    associationUserRoleResponse.setError(error);
    return associationUserRoleResponse;
  }

  public static AssociationUserRoleResponseBuilder create() {
    return new AssociationUserRoleResponseBuilder();
  }
}
