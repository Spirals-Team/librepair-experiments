package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.Builder;

public class WidgetCreateFormBuilder extends Builder<WidgetCreateForm> {

  private String type;
  private String name;
  private String localeCode;

  private WidgetCreateFormBuilder() {

  }

  public WidgetCreateFormBuilder type(String type) {
    this.type = type;
    return this;
  }

  public WidgetCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WidgetCreateFormBuilder localeCode(String localeCode) {
    this.localeCode = localeCode;
    return this;
  }

  @Override
  public WidgetCreateForm build() {
    WidgetCreateForm form = new WidgetCreateForm();
    form.setName(name);
    form.setType(type);
    form.setLocaleCode(localeCode);
    return form;
  }

  public static WidgetCreateFormBuilder create() {
    return new WidgetCreateFormBuilder();
  }
}
