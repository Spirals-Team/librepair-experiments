package com.cmpl.web.core.factory.role;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface RoleManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllRoles(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateRole(Locale locale);

  ModelAndView computeModelAndViewForUpdateRole(Locale locale, String roleId);

  ModelAndView computeModelAndViewForUpdateRoleMain(Locale locale, String roleId);

  ModelAndView computeModelAndViewForUpdateRolePrivileges(Locale locale, String roleId);

}
