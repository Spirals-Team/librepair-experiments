package com.cmpl.web.core.news.entry;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.NewsEntry;

public class NewsEntryBuilder extends BaseBuilder<NewsEntry> {

  private String facebookId;

  private String contentId;
  private String imageId;

  private String author;
  private String tags;
  private String title;

  private NewsEntryBuilder() {

  }

  public NewsEntryBuilder facebookId(String facebookId) {
    this.facebookId = facebookId;
    return this;
  }

  public NewsEntryBuilder contentId(String contentId) {
    this.contentId = contentId;
    return this;
  }

  public NewsEntryBuilder imageId(String imageId) {
    this.imageId = imageId;
    return this;
  }

  public NewsEntryBuilder author(String author) {
    this.author = author;
    return this;
  }

  public NewsEntryBuilder tags(String tags) {
    this.tags = tags;
    return this;
  }

  public NewsEntryBuilder title(String title) {
    this.title = title;
    return this;
  }

  @Override
  public NewsEntry build() {
    NewsEntry newsEntry = new NewsEntry();
    newsEntry.setCreationDate(creationDate);
    newsEntry.setCreationUser(creationUser);
    newsEntry.setModificationUser(modificationUser);
    newsEntry.setId(id);
    newsEntry.setModificationDate(modificationDate);
    newsEntry.setAuthor(author);
    newsEntry.setTags(tags);
    newsEntry.setTitle(title);
    newsEntry.setContentId(contentId);
    newsEntry.setImageId(imageId);
    newsEntry.setFacebookId(facebookId);

    return newsEntry;
  }

  public static NewsEntryBuilder create() {
    return new NewsEntryBuilder();
  }
}
