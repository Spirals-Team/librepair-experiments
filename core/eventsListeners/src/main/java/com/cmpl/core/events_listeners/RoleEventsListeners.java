package com.cmpl.core.events_listeners;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.role.PrivilegeService;
import com.cmpl.web.core.role.RoleDTO;

public class RoleEventsListeners {

  private final AssociationUserRoleService associationUserRoleService;
  private final PrivilegeService privilegeService;

  public RoleEventsListeners(AssociationUserRoleService associationUserRoleService, PrivilegeService privilegeService) {
    this.associationUserRoleService = associationUserRoleService;
    this.privilegeService = privilegeService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseDTO> clazz = deletedEvent.getDto().getClass();
    if (RoleDTO.class.equals(clazz)) {
      RoleDTO deletedRole = (RoleDTO) deletedEvent.getDto();
      if (deletedRole != null) {
        String roleId = String.valueOf(deletedRole.getId());
        associationUserRoleService.findByRoleId(roleId)
            .forEach(associationUserRoleDTO -> associationUserRoleService.deleteEntity(associationUserRoleDTO.getId()));
        privilegeService.findByRoleId(roleId)
            .forEach(privilegeDTO -> privilegeService.deleteEntity(privilegeDTO.getId()));
      }

    }
  }

}
