package com.cmpl.web.configuration.core.common;

import java.io.FileReader;
import java.util.Properties;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import com.cmpl.web.core.common.mail.DoNothingMailSenderImpl;
import com.cmpl.web.core.common.mail.MailSender;
import com.cmpl.web.core.common.mail.MailSenderImpl;
import com.cmpl.web.core.common.message.WebMessageSource;

@Configuration
@PropertySource("classpath:/core/core.properties")
public class MailConfiguration {

  @Value("${mailSenderConfigurationFile}")
  String mailSenderConfigurationFile;

  @Value("${baseUrl}")
  String baseUrl;

  @Value("${mailFrom}")
  String from;

  @Value("${mailFilters}")
  Set<String> filters;

  @Bean
  public ResourceBundleMessageSource emailMessageSource() {
    final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("i18n/mails");
    return messageSource;
  }

  @Bean
  MailSender fakeMailSender() {
    return new DoNothingMailSenderImpl();
  }

  @Bean
  MailSender mailSender(JavaMailSender javaMailSender, TemplateEngine templateEngine, WebMessageSource messageSource) {
    return new MailSenderImpl(javaMailSender, templateEngine, filters, messageSource, from, baseUrl);
  }

  @Bean
  JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    Properties mailProperties = new Properties();
    JSONObject object = computeProperties();
    mailProperties.put("mail.smtp.auth", Boolean.parseBoolean(String.valueOf(object.get("mailSmtpAuth"))));
    mailProperties.put("mail.smtp.starttls.enable",
        Boolean.parseBoolean(String.valueOf(object.get("mailSmtpStarttlsEnable"))));
    mailSender.setJavaMailProperties(mailProperties);
    mailSender.setHost(String.valueOf(object.get("host")));
    mailSender.setPort(Integer.parseInt(String.valueOf(object.get("port"))));
    mailSender.setUsername(String.valueOf(object.get("username")));
    mailSender.setPassword(String.valueOf(object.get("password")));
    return mailSender;
  }

  JSONObject computeProperties() {
    JSONParser parser = new JSONParser();
    try {
      return (JSONObject) parser.parse(new FileReader(mailSenderConfigurationFile));
    } catch (Exception e) {
      return null;
    }
  }

}
