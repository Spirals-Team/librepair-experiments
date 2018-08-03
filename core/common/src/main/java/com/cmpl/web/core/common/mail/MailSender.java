package com.cmpl.web.core.common.mail;

import java.util.Locale;

import org.thymeleaf.context.Context;

public interface MailSender {

  void sendMail(String htmlTemplate, Context context, String mailSubject, Locale locale, String... mailTo)
      throws Exception;
}
