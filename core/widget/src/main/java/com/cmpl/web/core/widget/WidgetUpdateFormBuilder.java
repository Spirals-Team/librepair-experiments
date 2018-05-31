package com.cmpl.web.core.widget;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class WidgetUpdateFormBuilder extends Builder<WidgetUpdateForm> {

  private String type;
  private String entityId;
  private String name;
  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;
  private String personalization;
  private String localeCode;
  private String toolTipKey;

  public WidgetUpdateFormBuilder type(String type) {
    this.type = type;
    return this;
  }

  public WidgetUpdateFormBuilder entityId(String entityId) {
    this.entityId = entityId;
    return this;
  }

  public WidgetUpdateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WidgetUpdateFormBuilder localeCode(String localeCode) {
    this.localeCode = localeCode;
    return this;
  }

  public WidgetUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public WidgetUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public WidgetUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public WidgetUpdateFormBuilder personalization(String personalization) {
    this.personalization = personalization;
    return this;
  }

  public WidgetUpdateFormBuilder toolTipKey(String toolTipKey) {
    this.toolTipKey = toolTipKey;
    return this;
  }

  public WidgetUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public WidgetUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  private WidgetUpdateFormBuilder() {

  }

  @Override
  public WidgetUpdateForm build() {
    WidgetUpdateForm form = new WidgetUpdateForm();
    form.setCreationDate(creationDate);
    form.setEntityId(entityId);
    form.setId(id);
    form.setModificationDate(modificationDate);
    form.setName(name);
    form.setType(type);
    form.setPersonalization(personalization);
    form.setLocaleCode(localeCode);
    form.setToolTipKey(toolTipKey);
    form.setCreationUser(creationUser);
    form.setModificationUser(modificationUser);
    return form;
  }

  public static WidgetUpdateFormBuilder create() {
    return new WidgetUpdateFormBuilder();
  }
}
