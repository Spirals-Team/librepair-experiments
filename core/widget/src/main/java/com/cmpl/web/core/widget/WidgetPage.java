package com.cmpl.web.core.widget;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "widget_page")
@Table(name = "widget_page", indexes = {
    @Index(name = "IDX_WIDGET_PAGE", columnList = "page_id,widget_id", unique = true)})
public class WidgetPage extends BaseEntity {

  @Column(name = "page_id", nullable = false)
  private String pageId;

  @Column(name = "widget_id", nullable = false)
  private String widgetId;

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getWidgetId() {
    return widgetId;
  }

  public void setWidgetId(String widgetId) {
    this.widgetId = widgetId;
  }
}
