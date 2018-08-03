package com.cmpl.web.core.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.role.privilege.PrivilegeDTO;
import com.cmpl.web.core.role.privilege.PrivilegeDTOBuilder;
import com.cmpl.web.core.role.privilege.PrivilegeForm;
import com.cmpl.web.core.role.privilege.PrivilegeResponse;
import com.cmpl.web.core.role.privilege.PrivilegeResponseBuilder;
import com.cmpl.web.core.role.privilege.PrivilegeService;

public class RoleDispatcherImpl implements RoleDispatcher {

  private final RoleTranslator translator;
  private final RoleService service;
  private final PrivilegeService privilegeService;
  private final PluginRegistry<Privilege, String> privilegesRegistry;

  public RoleDispatcherImpl(RoleService service, PrivilegeService privilegeService, RoleTranslator translator,
      PluginRegistry<Privilege, String> privilegesRegistry) {
    this.service = Objects.requireNonNull(service);
    this.translator = Objects.requireNonNull(translator);
    this.privilegeService = Objects.requireNonNull(privilegeService);
    this.privilegesRegistry = Objects.requireNonNull(privilegesRegistry);
  }

  @Override
  public RoleResponse createEntity(RoleCreateForm form, Locale locale) {

    RoleDTO roleToCreate = translator.fromCreateFormToDTO(form);
    RoleDTO createdRole = service.createEntity(roleToCreate);

    return translator.fromDTOToResponse(createdRole);
  }

  @Override
  public RoleResponse updateEntity(RoleUpdateForm form, Locale locale) {

    RoleDTO roleToUpdate = service.getEntity(form.getId());
    roleToUpdate.setDescription(form.getDescription());
    roleToUpdate.setName(form.getName());

    RoleDTO roleUpdated = service.updateEntity(roleToUpdate);

    return translator.fromDTOToResponse(roleUpdated);
  }

  @Override
  public RoleResponse deleteEntity(String roleId, Locale locale) {
    service.deleteEntity(Long.parseLong(roleId));
    return RoleResponseBuilder.create().build();
  }

  @Override
  public PrivilegeResponse updateEntity(PrivilegeForm form, Locale locale) {

    List<PrivilegeDTO> privileges = privilegeService.findByRoleId(form.getRoleId());
    privileges.forEach(privilegeDTO -> privilegeService.deleteEntity(privilegeDTO.getId()));

    List<PrivilegeDTO> privilegesDTOToAdd = computePrivilegesToCreate(form);

    privilegesDTOToAdd.forEach(privilegeDTOToAdd -> privilegeService.createEntity(privilegeDTOToAdd));
    return PrivilegeResponseBuilder.create().build();
  }

  List<PrivilegeDTO> computePrivilegesToCreate(PrivilegeForm form) {
    List<PrivilegeDTO> privilegesDTOToAdd = new ArrayList<>();
    if (isAll(form)) {
      privilegesRegistry.getPlugins().forEach(privilege -> {
        privilegesDTOToAdd
            .add(PrivilegeDTOBuilder.create().content(privilege.privilege()).roleId(form.getRoleId()).build());
      });
    } else {
      form.getPrivilegesToEnable().forEach(privilegeToEnable -> privilegesDTOToAdd
          .add(PrivilegeDTOBuilder.create().content(privilegeToEnable).roleId(form.getRoleId()).build()));
    }
    return privilegesDTOToAdd;
  }

  private boolean isAll(PrivilegeForm form) {
    return form.getPrivilegesToEnable().stream().filter(privilege -> "all:all:all".equals(privilege))
        .collect(Collectors.toList()).contains(true);

  }
}
