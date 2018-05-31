package com.cmpl.web.core.common.error;

public enum ERROR_CAUSE {

  EMPTY_NEWS_ID("empty.id"),
  EMPTY_NEWS_TITLE("empty.title"),
  EMPTY_NEWS_AUTHOR("empty.author"),
  EMPTY_NEWS_CONTENT("empty.content"),
  EMPTY_NEWS_LEGEND("empty.legend"),
  INVALID_NEWS_FORMAT("invalid.format"),
  EMPTY_NEWS_ALT("empty.alt"),

  EMPTY_PAGE_NAME("empty.name"),
  EMPTY_PAGE_MENU_TITLE("empty.menuTitle"),
  USED_NAME("used.name"),
  USED_MENU_TITLE("used.menuTitle"),

  EMPTY_MENU_TITLE("empty.menu.title"),
  EMPTY_PAGE("empty.menu.page"),
  BAD_ORDER("bad.menu.order"),

  EMPTY_CAROUSEL_NAME("empty.carousel.name"),
  EMPTY_CAROUSEL_ID("empty.carousel.id"),
  EMPTY_MEDIA_ID("empty.media.id"),
  EMPTY_CAROUSEL_PAGE("empty.carousel.page"),
  EMPTY_CAROUSEL_ITEM_ID("empty.carousel.item.id"),

  EMPTY_WIDGET_NAME("empty.widget.name"),
  EMPTY_WIDGET_TYPE("empty.widget.type"),
  EMPTY_WIDGET_ID("empty.widget.id"),
  EMPTY_WIDGET_PAGE_ID("empty.widget.page.id"),

  EMPTY_USER_ID("empty.user.id"),
  EMPTY_ROLE_ID("empty.role.id"),
  EMPTY_PRIVILEGES("empty.role.privileges"),
  EMPTY_USER_ROLE_ID("empty.user.role.id"),

  EMPTY_LOGIN("empty.user.login"),
  EMPTY_EMAIL("empty.user.email"),

  EMPTY_ROLE_NAME("empty.role.name"),
  EMPTY_ROLE_DESCRIPTION("empty.role.description"),

  EMPTY_PASSWORD("empty.user.password"),
  PASSWORD_CONFIRMATION_DIFFERENT("different.password.confirmation"),
  PASSWORD_SAME_AS_OLD("same.password.as.before"),
  PASSWORD_NOT_STRONG("password.not.strong"),
  EMPTY_CONFIRMATION("empty.user.confirmation"),
  INVALID_TOKEN("token.invalid");

  private String causeKey;

  ERROR_CAUSE(String causeKey) {
    this.causeKey = causeKey;
  }

  public String getCauseKey() {
    return causeKey;
  }

}
