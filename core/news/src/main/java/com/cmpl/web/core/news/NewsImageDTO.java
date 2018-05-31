package com.cmpl.web.core.news;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.media.MediaDTO;

/**
 * DTO NewsImage
 * 
 * @author Louis
 *
 */
public class NewsImageDTO extends BaseDTO {

  private MediaDTO media;
  private String legend;
  private String alt;

  public MediaDTO getMedia() {
    return media;
  }

  public void setMedia(MediaDTO media) {
    this.media = media;
  }

  public String getLegend() {
    return legend;
  }

  public void setLegend(String legend) {
    this.legend = legend;
  }

  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }

}
