package com.cmpl.web.core.widget;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class WidgetUpdateForm extends BaseUpdateForm<WidgetDTO> {

  @NotBlank(message = "empty.widget.type")
  private String type;
  private String entityId;
  @NotBlank(message = "empty.widget.name")
  private String name;
  private String personalization;
  private String localeCode;
  private String toolTipKey;
  @NotNull
  private Boolean asynchronous;

  public WidgetUpdateForm() {

  }

  public WidgetUpdateForm(WidgetDTO dto, String localeCode, String toolTipKey) {
    super(dto);
    this.type = dto.getType();
    this.entityId = dto.getEntityId();
    this.name = dto.getName();
    this.personalization = dto.getPersonalization();
    this.localeCode = localeCode;
    this.toolTipKey = toolTipKey;
    this.asynchronous = dto.isAsynchronous();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPersonalization() {
    return personalization;
  }

  public void setPersonalization(String personalization) {
    this.personalization = personalization;
  }

  public String getLocaleCode() {
    return localeCode;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }

  public String getToolTipKey() {
    return toolTipKey;
  }

  public void setToolTipKey(String toolTipKey) {
    this.toolTipKey = toolTipKey;
  }

  public Boolean getAsynchronous() {
    return asynchronous;
  }

  public void setAsynchronous(Boolean asynchronous) {
    this.asynchronous = asynchronous;
  }
}
