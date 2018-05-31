package com.cmpl.web.core.news;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class NewsContentBuilder extends BaseBuilder<NewsContent> {

  private String content;
  private String linkUrl;
  private String videoUrl;

  private NewsContentBuilder() {

  }

  public NewsContentBuilder content(String content) {
    this.content = content;
    return this;
  }

  public NewsContentBuilder linkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    return this;
  }

  public NewsContentBuilder videoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  @Override
  public NewsContent build() {
    NewsContent newsContent = new NewsContent();
    newsContent.setId(id);
    newsContent.setCreationDate(creationDate);
    newsContent.setModificationDate(modificationDate);
    newsContent.setContent(content);
    newsContent.setLinkUrl(linkUrl);
    newsContent.setVideoUrl(videoUrl);

    return newsContent;
  }

  public static NewsContentBuilder create() {
    return new NewsContentBuilder();
  }
}
