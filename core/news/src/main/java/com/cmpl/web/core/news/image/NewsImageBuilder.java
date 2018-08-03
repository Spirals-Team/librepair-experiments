package com.cmpl.web.core.news.image;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.NewsImage;

public class NewsImageBuilder extends BaseBuilder<NewsImage> {

  private String mediaId;

  private String legend;

  private String alt;

  public NewsImageBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  public NewsImageBuilder legend(String legend) {
    this.legend = legend;
    return this;
  }

  public NewsImageBuilder alt(String alt) {
    this.alt = alt;
    return this;
  }

  private NewsImageBuilder() {

  }

  @Override
  public NewsImage build() {
    NewsImage newsImage = new NewsImage();
    newsImage.setCreationDate(creationDate);
    newsImage.setCreationUser(creationUser);
    newsImage.setModificationUser(modificationUser);
    newsImage.setId(id);
    newsImage.setModificationDate(modificationDate);
    newsImage.setAlt(alt);
    newsImage.setLegend(legend);
    newsImage.setMediaId(mediaId);
    return newsImage;
  }

  public static NewsImageBuilder create() {
    return new NewsImageBuilder();
  }
}
