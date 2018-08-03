package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.Builder;

public class StyleCreateFormBuilder extends Builder<StyleCreateForm> {

  private String content;
  private String name;

  private StyleCreateFormBuilder() {

  }

  public StyleCreateFormBuilder content(String content) {
    this.content = content;
    return this;
  }

  public StyleCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public StyleCreateForm build() {
    StyleCreateForm form = new StyleCreateForm();
    form.setContent(content);
    form.setName(name);
    return form;
  }

  public static StyleCreateFormBuilder create() {
    return new StyleCreateFormBuilder();
  }
}
