package com.cmpl.web.core.design;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

public interface DesignDispatcher {

  DesignResponse createEntity(DesignCreateForm createForm, Locale locale) throws BaseException;

  void deleteEntity(Long websiteId, Long styleId) throws BaseException;

}
