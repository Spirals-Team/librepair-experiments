package com.cmpl.web.core.factory.page;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface PageManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllPages(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForUpdatePage(Locale locale, String pageId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdatePageMain(Locale locale, String pageId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdatePageBody(Locale locale, String pageId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdatePageHeader(Locale locale, String pageId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdatePageFooter(Locale locale, String pageId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdatePageMeta(Locale locale, String pageId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdatePageAMP(Locale locale, String pageId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForCreatePage(Locale locale);

  ModelAndView computeModelAndViewForUpdatePageWidgets(Locale locale, String pageId,
      String personalizationLanguageCode);
}
