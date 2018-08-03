package com.cmpl.web.core.common.notification;

import java.util.List;

import org.springframework.validation.FieldError;

public class Notification {

  private String type;
  private String content;
  private List<FieldError> errors;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<FieldError> getErrors() {
    return errors;
  }

  public void setErrors(List<FieldError> errors) {
    this.errors = errors;
  }
}
