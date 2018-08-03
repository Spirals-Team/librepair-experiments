package com.cmpl.web.core.common.builder;

import org.springframework.data.domain.Page;

import com.cmpl.web.core.common.resource.PageWrapper;

public class PageWrapperBuilder<T> extends Builder<PageWrapper<T>> {

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

  public PageWrapperBuilder<T> page(Page<T> page) {
    this.page = page;
    return this;
  }

  public PageWrapperBuilder<T> firstPage(boolean isFirstPage) {
    this.isFirstPage = isFirstPage;
    return this;
  }

  public PageWrapperBuilder<T> lastPage(boolean isLastPage) {
    this.isLastPage = isLastPage;
    return this;
  }

  public PageWrapperBuilder<T> totalPages(int totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  public PageWrapperBuilder<T> currentPageNumber(int currentPageNumber) {
    this.currentPageNumber = currentPageNumber;
    return this;
  }

  public PageWrapperBuilder<T> pageBaseUrl(String pageBaseUrl) {
    this.pageBaseUrl = pageBaseUrl;
    return this;
  }

  public PageWrapperBuilder<T> pageLabel(String pageLabel) {
    this.pageLabel = pageLabel;
    return this;
  }

  public PageWrapperBuilder<T> itemLink(String itemLink) {
    this.itemLink = itemLink;
    return this;
  }

  public PageWrapperBuilder<T> createItemLink(String createItemLink) {
    this.createItemLink = createItemLink;
    return this;
  }

  public PageWrapperBuilder<T> createItemPrivilege(String createItemPrivilege) {
    this.createItemPrivilege = createItemPrivilege;
    return this;
  }

  @Override
  public PageWrapper<T> build() {
    PageWrapper<T> wrapper = new PageWrapper<>();
    wrapper.setCurrentPageNumber(currentPageNumber);
    wrapper.setFirstPage(isFirstPage);
    wrapper.setLastPage(isLastPage);
    wrapper.setPage(page);
    wrapper.setPageBaseUrl(pageBaseUrl);
    wrapper.setPageLabel(pageLabel);
    wrapper.setTotalPages(totalPages);
    wrapper.setCreateItemLink(createItemLink);
    wrapper.setCreateItemPrivilege(createItemPrivilege);
    wrapper.setItemLink(itemLink);
    return wrapper;
  }

}
