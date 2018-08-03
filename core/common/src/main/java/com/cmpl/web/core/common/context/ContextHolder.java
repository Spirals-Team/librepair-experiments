package com.cmpl.web.core.common.context;

import java.time.format.DateTimeFormatter;

/**
 * Holder des donnees de configuration globales de l'application
 * 
 * @author Louis
 *
 */
public class ContextHolder {

  private DateTimeFormatter dateFormat;
  private String templateBasePath;
  private int elementsPerPage;
  private String mediaBasePath;
  private String websiteUrl;

  public DateTimeFormatter getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(DateTimeFormatter dateFormat) {
    this.dateFormat = dateFormat;
  }

  public int getElementsPerPage() {
    return elementsPerPage;
  }

  public void setElementsPerPage(int elementsPerPage) {
    this.elementsPerPage = elementsPerPage;
  }

  public String getTemplateBasePath() {
    return templateBasePath;
  }

  public void setTemplateBasePath(String templateBasePath) {
    this.templateBasePath = templateBasePath;
  }

  public String getMediaBasePath() {
    return mediaBasePath;
  }

  public void setMediaBasePath(String mediaBasePath) {
    this.mediaBasePath = mediaBasePath;
  }

  public String getWebsiteUrl() {
    return websiteUrl;
  }

  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
  }
}
