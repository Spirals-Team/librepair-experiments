package com.cmpl.web.core.factory.media;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

public interface MediaManagerDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMedias(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForViewMedia(String mediaId, Locale locale);

  ModelAndView computeModelAndViewForViewMediaMain(String mediaId);

  ModelAndView computeModelAndViewForUploadMedia(Locale locale);

}
