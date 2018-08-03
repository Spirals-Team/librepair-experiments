package com.cmpl.web.core.style;

import javax.validation.constraints.NotBlank;

public class StyleCreateForm {

  private String content;
  @NotBlank
  private String name;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
