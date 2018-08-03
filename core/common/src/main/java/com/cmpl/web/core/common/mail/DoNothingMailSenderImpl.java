package com.cmpl.web.core.common.mail;

import java.util.Arrays;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;

public class DoNothingMailSenderImpl implements MailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(DoNothingMailSenderImpl.class);

  @Override
  public void sendMail(String htmlTemplate, Context context, String mailSubject, Locale locale, String... mailTo)
      throws Exception {

    LOGGER.info(
        "A mail with recipient(s) '{}' and subject '{}' was not sent because no java mail sender is configured",
        Arrays.toString(mailTo), mailSubject);
  }
}
