package com.cmpl.web.core.common.resource;

import org.springframework.data.domain.Page;

public class PageWrapper<T> {

  private Page<T> page;
  private boolean isFirstPage;
  private boolean isLastPage;
  private int totalPages;
  private int currentPageNumber;
  private String pageBaseUrl;
  private String pageLabel;
  private String itemLink;
  private String createItemLink;
  private String createItemPrivilege;

  public Page<T> getPage() {
    return page;
  }

  public void setPage(Page<T> page) {
    this.page = page;
  }

  public boolean isFirstPage() {
    return isFirstPage;
  }

  public void setFirstPage(boolean isFirstPage) {
    this.isFirstPage = isFirstPage;
  }

  public boolean isLastPage() {
    return isLastPage;
  }

  public void setLastPage(boolean isLastPage) {
    this.isLastPage = isLastPage;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getCurrentPageNumber() {
    return currentPageNumber;
  }

  public void setCurrentPageNumber(int currentPageNumber) {
    this.currentPageNumber = currentPageNumber;
  }

  public String getPageBaseUrl() {
    return pageBaseUrl;
  }

  public void setPageBaseUrl(String pageBaseUrl) {
    this.pageBaseUrl = pageBaseUrl;
  }

  public String getPageLabel() {
    return pageLabel;
  }

  public void setPageLabel(String pageLabel) {
    this.pageLabel = pageLabel;
  }

  public String getItemLink() {
    return itemLink;
  }

  public void setItemLink(String itemLink) {
    this.itemLink = itemLink;
  }

  public String getCreateItemLink() {
    return createItemLink;
  }

  public void setCreateItemLink(String createItemLink) {
    this.createItemLink = createItemLink;
  }

  public String getCreateItemPrivilege() {
    return createItemPrivilege;
  }

  public void setCreateItemPrivilege(String createItemPrivilege) {
    this.createItemPrivilege = createItemPrivilege;
  }
}
