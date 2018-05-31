package com.cmpl.web.core.factory;

import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;

public abstract class AbstractBackDisplayFactoryImpl<T> extends BackDisplayFactoryImpl {

  public AbstractBackDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
  }

  public PageWrapper<T> computePageWrapper(Locale locale, int pageNumber) {
    Page<T> pagedDTOEntries = computeEntries(locale, pageNumber);

    boolean isFirstPage = pagedDTOEntries.isFirst();
    boolean isLastPage = pagedDTOEntries.isLast();
    int totalPages = pagedDTOEntries.getTotalPages();
    int currentPageNumber = pagedDTOEntries.getNumber();

    return new PageWrapperBuilder<T>().currentPageNumber(currentPageNumber).firstPage(isFirstPage).lastPage(isLastPage)
        .page(pagedDTOEntries).totalPages(totalPages).pageBaseUrl(getBaseUrl()).createItemLink(getCreateItemLink())
        .createItemPrivilege(getCreateItemPrivilege()).itemLink(getItemLink())
        .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages)).build();
  }

  protected abstract String getBaseUrl();

  protected abstract String getItemLink();

  protected String getCreateItemLink() {
    return getBaseUrl() + "/_create";
  }

  protected abstract String getCreateItemPrivilege();

  protected abstract Page<T> computeEntries(Locale locale, int pageNumber);

  protected boolean canAddBreadCrumbItem(BreadCrumb breadCrumb, BreadCrumbItem item) {
    for (BreadCrumbItem itemFromModel : breadCrumb.getItems()) {
      if (itemFromModel.getText().equals(item.getText())) {
        return false;
      }
    }
    return true;
  }
}
