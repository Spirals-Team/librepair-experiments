package com.cmpl.web.manager.ui.core.role;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.common.resource.BaseResponse;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.role.PrivilegeForm;
import com.cmpl.web.core.role.PrivilegeResponse;
import com.cmpl.web.core.role.RoleCreateForm;
import com.cmpl.web.core.role.RoleDispatcher;
import com.cmpl.web.core.role.RoleResponse;
import com.cmpl.web.core.role.RoleUpdateForm;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/roles")
public class RoleManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RoleManagerController.class);

  private final RoleDispatcher roleDispatcher;
  private final RoleManagerDisplayFactory roleManagerDisplayFactory;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public RoleManagerController(RoleDispatcher roleDispatcher, RoleManagerDisplayFactory roleManagerDisplayFactory,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {
    this.roleDispatcher = roleDispatcher;
    this.roleManagerDisplayFactory = roleManagerDisplayFactory;
    this.notificationCenter = notificationCenter;
    this.messageSource = messageSource;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewRoles(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_VIEW.name());
    return roleManagerDisplayFactory.computeModelAndViewForViewAllRoles(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('administration:roles:create')")
  public ModelAndView printCreateRole(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_CREATE.name());
    return roleManagerDisplayFactory.computeModelAndViewForCreateRole(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:roles:create')")
  public ResponseEntity<RoleResponse> createRole(@RequestBody RoleCreateForm createForm, Locale locale) {
    LOGGER.info("Tentative de création d'un role");
    try {
      RoleResponse response = roleDispatcher.createEntity(createForm, locale);
      if (response.getRole() != null) {
        LOGGER.info("Entrée crée, id " + response.getRole().getId());
      }
      if (response.getError() == null) {
        notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));
      } else {
        notificationCenter.sendNotification(response.getError());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping(value = "/{roleId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:roles:write')")
  public ResponseEntity<RoleResponse> updateRole(@RequestBody RoleUpdateForm updateForm, Locale locale) {

    LOGGER.info("Tentative de modification d'un role");
    try {
      RoleResponse response = roleDispatcher.updateEntity(updateForm, locale);
      if (response.getRole() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getRole().getId());
      }
      if (response.getError() == null) {
        notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));
      } else {
        notificationCenter.sendNotification(response.getError());
      }

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{roleId}")
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewUpdateRole(@PathVariable(value = "roleId") String roleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + roleId);
    return roleManagerDisplayFactory.computeModelAndViewForUpdateRole(locale, roleId);
  }

  @GetMapping(value = "/{roleId}/_main")
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewUpdateRoleMain(@PathVariable(value = "roleId") String roleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + roleId + " pour la partie main");
    return roleManagerDisplayFactory.computeModelAndViewForUpdateRoleMain(locale, roleId);
  }

  @GetMapping(value = "/{roleId}/_privileges")
  @PreAuthorize("hasAuthority('administration:roles:read')")
  public ModelAndView printViewUpdateRolePrivileges(@PathVariable(value = "roleId") String roleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + roleId + " pour la partie privileges");
    return roleManagerDisplayFactory.computeModelAndViewForUpdateRolePrivileges(locale, roleId);
  }

  @PutMapping(value = "/{roleId}/privileges", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:roles:write')")
  public ResponseEntity<PrivilegeResponse> updateRolePrivileges(@RequestBody PrivilegeForm privilegeForm,
      Locale locale) {

    LOGGER.info("Tentative de modification des privileges d'un role");
    try {
      PrivilegeResponse response = roleDispatcher.updateEntity(privilegeForm, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/{roleId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:roles:delete')")
  public ResponseEntity<BaseResponse> deleteRole(@PathVariable(value = "roleId") String roleId, Locale locale) {
    LOGGER.info("Tentative de suppression d'un role");
    try {
      BaseResponse response = roleDispatcher.deleteEntity(roleId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Role " + roleId + " supprimé");
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression du role " + roleId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(

          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
