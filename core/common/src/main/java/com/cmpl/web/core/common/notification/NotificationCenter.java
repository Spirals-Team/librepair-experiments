package com.cmpl.web.core.common.notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.error.Error;

public class NotificationCenter {

  private final SimpMessagingTemplate template;
  private static final String WEBSOCKET_DOMAIN = "/notifications";

  public NotificationCenter(SimpMessagingTemplate template) {
    this.template = template;
  }

  public void sendNotification(Error error) {
    String messageToSend = error.getCauses().stream().map(cause -> cause.getMessage() + "<br>")
        .collect(Collectors.joining());
    sendNotification(null, messageToSend);
  }

  public void sendNotification(String type, String messageToSend) {
    String notificationType = type;
    if (!StringUtils.hasText(notificationType)) {
      notificationType = "danger";
    }

    senNotificationViaExecutor(NotificationBuilder.create().content(messageToSend).type(notificationType).build());
  }

  private void senNotificationViaExecutor(Notification notification) {

    ExecutorService notificationExecutor = Executors.newCachedThreadPool();
    notificationExecutor.execute(new Runnable() {
      @Override
      public void run() {

        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {

        }

        template.convertAndSend(WEBSOCKET_DOMAIN, notification);
      }
    });
  }
}
