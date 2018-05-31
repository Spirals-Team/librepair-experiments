package com.cmpl.web.core.widget;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

public interface WidgetDispatcher {

  WidgetResponse createEntity(WidgetCreateForm form, Locale locale);

  WidgetResponse updateEntity(WidgetUpdateForm form, Locale locale);

  WidgetResponse deleteEntity(String widgetId, Locale locale);

  WidgetPageResponse createEntity(String pageId, WidgetPageCreateForm form, Locale locale);

  void deleteEntity(String pageId, String widgetId, Locale locale) throws BaseException;

}
