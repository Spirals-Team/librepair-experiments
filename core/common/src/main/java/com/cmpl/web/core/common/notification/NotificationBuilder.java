package com.cmpl.web.core.common.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

import com.cmpl.web.core.common.builder.Builder;

public class NotificationBuilder extends Builder<Notification> {

  private String type;
  private String content;
  private List<FieldError> errors;

  private NotificationBuilder() {
    errors = new ArrayList<>();
  }

  public NotificationBuilder type(String type) {
    this.type = type;
    return this;
  }

  public NotificationBuilder content(String content) {
    this.content = content;
    return this;
  }

  public NotificationBuilder errors(List<FieldError> errors) {
    this.errors.addAll(errors);
    return this;
  }

  @Override
  public Notification build() {
    Notification notification = new Notification();
    notification.setContent(content);
    notification.setType(type);
    notification.setErrors(errors);
    return notification;
  }

  public static NotificationBuilder create() {
    return new NotificationBuilder();
  }
}
