package com.cmpl.web.core.factory.website;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

public interface WebsiteManagerDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllWebsites(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateWebsite(Locale locale);

  ModelAndView computeModelAndViewForUpdateWebsite(Locale locale, String websiteId);

  ModelAndView computeModelAndViewForUpdateWebsiteMain(Locale locale, String websiteId);

  ModelAndView computeModelAndViewForUpdateWebsiteSitemap(Locale locale, String websiteId);

  ModelAndView computeModelAndViewForUpdateWebsiteDesign(Locale locale, String websiteId);
}
