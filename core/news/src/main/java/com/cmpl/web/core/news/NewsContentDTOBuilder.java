package com.cmpl.web.core.news;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class NewsContentDTOBuilder extends BaseBuilder<NewsContentDTO> {

  private String content;
  private String linkUrl;
  private String videoUrl;

  private NewsContentDTOBuilder() {

  }

  public NewsContentDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  public NewsContentDTOBuilder linkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
    return this;
  }

  public NewsContentDTOBuilder videoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  @Override
  public NewsContentDTO build() {
    NewsContentDTO newsContentDTO = new NewsContentDTO();
    newsContentDTO.setId(id);
    newsContentDTO.setCreationDate(creationDate);
    newsContentDTO.setModificationDate(modificationDate);
    newsContentDTO.setContent(content);
    newsContentDTO.setLinkUrl(linkUrl);
    newsContentDTO.setVideoUrl(videoUrl);

    return newsContentDTO;
  }

  public static NewsContentDTOBuilder create() {
    return new NewsContentDTOBuilder();
  }

}
