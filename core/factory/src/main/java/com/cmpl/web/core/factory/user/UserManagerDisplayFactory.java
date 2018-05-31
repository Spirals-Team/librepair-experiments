package com.cmpl.web.core.factory.user;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface UserManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllUsers(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateUser(Locale locale);

  ModelAndView computeModelAndViewForUpdateUser(Locale locale, String userId);

  ModelAndView computeModelAndViewForUpdateUserMain(Locale locale, String userId);

  ModelAndView computeModelAndViewForUpdateUserRoles(Locale locale, String userId);

}
