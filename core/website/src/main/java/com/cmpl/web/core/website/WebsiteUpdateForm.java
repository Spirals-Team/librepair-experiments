package com.cmpl.web.core.website;

import javax.validation.constraints.NotBlank;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class WebsiteUpdateForm extends BaseUpdateForm<WebsiteDTO> {

  @NotBlank(message = "empty.role.name")
  private String name;
  @NotBlank(message = "empty.role.description")
  private String description;

  private Boolean secure;

  public WebsiteUpdateForm() {

  }

  public WebsiteUpdateForm(WebsiteDTO websiteDTO) {
    super(websiteDTO);
    this.name = websiteDTO.getName();
    this.secure = websiteDTO.isSecure();
    this.description = websiteDTO.getDescription();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getSecure() {
    return secure;
  }

  public void setSecure(Boolean secure) {
    this.secure = secure;
  }
}
