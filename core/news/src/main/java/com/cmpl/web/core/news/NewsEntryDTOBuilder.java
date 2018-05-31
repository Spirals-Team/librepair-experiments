package com.cmpl.web.core.news;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class NewsEntryDTOBuilder extends BaseBuilder<NewsEntryDTO> {

  private String facebookId;

  private NewsContentDTO newsContent;
  private NewsImageDTO newsImage;

  private String author;
  private String tags;
  private String title;
  private String name;

  private NewsEntryDTOBuilder() {

  }

  public NewsEntryDTOBuilder facebookId(String facebookId) {
    this.facebookId = facebookId;
    return this;
  }

  public NewsEntryDTOBuilder newsContent(NewsContentDTO newsContent) {
    this.newsContent = newsContent;
    return this;
  }

  public NewsEntryDTOBuilder newsImage(NewsImageDTO newsImage) {
    this.newsImage = newsImage;
    return this;
  }

  public NewsEntryDTOBuilder author(String author) {
    this.author = author;
    return this;
  }

  public NewsEntryDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NewsEntryDTOBuilder tags(String tags) {
    this.tags = tags;
    return this;
  }

  public NewsEntryDTOBuilder title(String title) {
    this.title = title;
    return this;
  }

  @Override
  public NewsEntryDTO build() {
    NewsEntryDTO newsEntryDTO = new NewsEntryDTO();

    newsEntryDTO.setId(id);
    newsEntryDTO.setCreationDate(creationDate);
    newsEntryDTO.setModificationDate(modificationDate);
    newsEntryDTO.setAuthor(author);
    newsEntryDTO.setTags(tags);
    newsEntryDTO.setTitle(title);
    newsEntryDTO.setNewsContent(newsContent);
    newsEntryDTO.setNewsImage(newsImage);
    newsEntryDTO.setFacebookId(facebookId);
    newsEntryDTO.setName(name);

    return newsEntryDTO;
  }

  public static NewsEntryDTOBuilder create() {
    return new NewsEntryDTOBuilder();
  }

}
