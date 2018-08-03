package com.cmpl.web.core.factory.role;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

public interface RoleManagerDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllRoles(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateRole(Locale locale);

  ModelAndView computeModelAndViewForUpdateRole(Locale locale, String roleId);

  ModelAndView computeModelAndViewForUpdateRoleMain(Locale locale, String roleId);

  ModelAndView computeModelAndViewForUpdateRolePrivileges(Locale locale, String roleId);

}
