package com.cmpl.web.core.common.notification;

import com.cmpl.web.core.common.builder.Builder;

public class NotificationBuilder extends Builder<Notification> {

  private String type;
  private String content;

  private NotificationBuilder() {

  }

  public NotificationBuilder type(String type) {
    this.type = type;
    return this;
  }

  public NotificationBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public Notification build() {
    Notification notification = new Notification();
    notification.setContent(content);
    notification.setType(type);
    return notification;
  }

  public static NotificationBuilder create() {
    return new NotificationBuilder();
  }
}
