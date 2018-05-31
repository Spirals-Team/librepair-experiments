package com.cmpl.web.core.news;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.media.MediaDTO;

public class NewsImageDTOBuilder extends BaseBuilder<NewsImageDTO> {

  private MediaDTO media;
  private String legend;
  private String alt;

  private NewsImageDTOBuilder() {

  }

  public NewsImageDTOBuilder media(MediaDTO media) {
    this.media = media;
    return this;
  }

  public NewsImageDTOBuilder legend(String legend) {
    this.legend = legend;
    return this;
  }

  public NewsImageDTOBuilder alt(String alt) {
    this.alt = alt;
    return this;
  }

  @Override
  public NewsImageDTO build() {
    NewsImageDTO newsImageDTO = new NewsImageDTO();
    newsImageDTO.setId(id);
    newsImageDTO.setCreationDate(creationDate);
    newsImageDTO.setModificationDate(modificationDate);
    newsImageDTO.setAlt(alt);
    newsImageDTO.setLegend(legend);
    newsImageDTO.setMedia(media);

    return newsImageDTO;
  }

  public static NewsImageDTOBuilder create() {
    return new NewsImageDTOBuilder();
  }

}
