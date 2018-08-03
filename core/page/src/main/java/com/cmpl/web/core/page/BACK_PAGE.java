package com.cmpl.web.core.page;

/**
 * Enumeration pour les pages du back office du site
 * 
 * @author Louis
 *
 */
public enum BACK_PAGE {

  INDEX("back/index", "back.index.title"),
  LOGIN("back/login", ""),
  FORGOTTEN_PASSWORD("back/forgotten_password", ""),
  CHANGE_PASSWORD("back/change_password", ""),
  NEWS_VIEW("back/news/view_news", "back.news.title"),
  NEWS_CREATE("back/news/create_news_entry", "back.news.title"),
  NEWS_UPDATE("back/news/edit_news_entry", "back.news.title"),
  FACEBOOK_ACCESS("back/facebook_access", "facebook.access.title"),
  FACEBOOK_IMPORT("back/facebook_import", "facebook.access.title"),
  MENUS_VIEW("back/menus/view_menus", "back.menus.title"),
  MENUS_CREATE("back/menus/create_menu", "back.menus.title"),
  MENUS_UPDATE("back/menus/edit_menu", "back.menus.title"),
  PAGES_VIEW("back/pages/view_pages", "back.pages.title"),
  PAGES_CREATE("back/pages/create_page", "back.pages.title"),
  PAGES_UPDATE("back/pages/edit_page", "back.pages.title"),
  MEDIA_VIEW("back/medias/view_medias", "back.medias.title"),
  MEDIA_VISUALIZE("back/medias/view_media", "back.medias.title"),
  MEDIA_UPLOAD("back/medias/upload_media", "back.medias.title"),
  CAROUSELS_VIEW("back/carousels/view_carousels", "back.carousels.title"),
  CAROUSELS_UPDATE("back/carousels/edit_carousel", "back.carousels.title"),
  CAROUSELS_CREATE("back/carousels/create_carousel", "back.carousels.title"),
  STYLES_VIEW("back/styles/view_styles", "back.style.title"),
  STYLES_CREATE("back/styles/create_style", "back.style.title"),
  STYLES_UPDATE("back/styles/edit_style", "back.style.title"),
  WIDGET_VIEW("back/widgets/view_widgets", "back.widgets.title"),
  WIDGET_CREATE("back/widgets/create_widget", "back.widgets.title"),
  WIDGET_UPDATE("back/widgets/edit_widget", "back.widgets.title"),
  USER_VIEW("back/users/view_users", "back.users.title"),
  USER_CREATE("back/users/create_user", "back.users.title"),
  USER_UPDATE("back/users/edit_user", "back.users.title"),
  ROLE_VIEW("back/roles/view_roles", "back.roles.title"),
  ROLE_CREATE("back/roles/create_role", "back.roles.title"),
  ROLE_UPDATE("back/roles/edit_role", "back.roles.title"),
  GROUP_VIEW("back/groups/view_groups", "back.groups.title"),
  GROUP_CREATE("back/groups/create_group", "back.groups.title"),
  GROUP_UPDATE("back/groups/edit_group", "back.groups.title"),
  WEBSITE_VIEW("back/websites/view_websites", "back.websites.title"),
  WEBSITE_CREATE("back/websites/create_website", "back.websites.title"),
  WEBSITE_UPDATE("back/websites/edit_website", "back.websites.title");

  private String tile;
  private String title;

  BACK_PAGE(String tile, String title) {
    this.tile = tile;
    this.title = title;
  }

  public String getTile() {
    return tile;
  }

  public String getTitle() {
    return title;
  }

}
