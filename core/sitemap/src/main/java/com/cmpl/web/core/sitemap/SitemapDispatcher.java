package com.cmpl.web.core.sitemap;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

public interface SitemapDispatcher {

  SitemapResponse createEntity(SitemapCreateForm createForm, Locale locale) throws BaseException;

  void deleteEntity(Long websiteId, Long pageId) throws BaseException;

}
