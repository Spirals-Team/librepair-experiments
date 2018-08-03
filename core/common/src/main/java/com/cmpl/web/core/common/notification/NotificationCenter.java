package com.cmpl.web.core.common.notification;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cmpl.web.core.common.message.WebMessageSource;

public class NotificationCenter {

  private final SimpMessagingTemplate template;
  private final WebMessageSource messageSource;
  private static final String WEBSOCKET_DOMAIN = "/notifications";
  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationCenter.class);

  public NotificationCenter(SimpMessagingTemplate template, WebMessageSource messageSource) {
    this.template = Objects.requireNonNull(template);
    this.messageSource = Objects.requireNonNull(messageSource);

  }

  public void sendNotification(String messageKey, BindingResult bindingResult, Locale locale) {
    List<FieldError> translatedErrors = bindingResult.getFieldErrors().stream()
        .map(fieldError -> new FieldError(fieldError.getObjectName(), fieldError.getField(),
            messageSource.getMessage(fieldError.getDefaultMessage(), locale)))
        .collect(Collectors.toList());
    sendNotification("danger", messageSource.getMessage(messageKey, locale), translatedErrors);
  }

  public void sendNotification(String type, String messageToSend) {
    String notificationType = type;
    if (!StringUtils.hasText(notificationType)) {
      notificationType = "danger";
    }

    senNotificationViaExecutor(NotificationBuilder.create().content(messageToSend).type(notificationType).build());
  }

  public void sendNotification(String type, String content, List<FieldError> errors) {
    String notificationType = type;
    if (!StringUtils.hasText(notificationType)) {
      notificationType = "danger";
    }

    senNotificationViaExecutor(
        NotificationBuilder.create().content(content).errors(errors).type(notificationType).build());
  }

  private void senNotificationViaExecutor(Notification notification) {

    ExecutorService notificationExecutor = Executors.newCachedThreadPool();
    notificationExecutor.execute(new Runnable() {
      @Override
      public void run() {

        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          LOGGER.error("Attente avant l'envoi de la notification interrompue", e);
        }

        template.convertAndSend(WEBSOCKET_DOMAIN, notification);
      }
    });
  }
}
