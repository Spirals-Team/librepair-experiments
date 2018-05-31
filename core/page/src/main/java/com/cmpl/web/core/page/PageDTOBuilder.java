package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class PageDTOBuilder extends BaseBuilder<PageDTO> {

  private String name;
  private String menuTitle;
  private String body;
  private String header;
  private String footer;
  private String meta;
  private String amp;

  private PageDTOBuilder() {

  }

  public PageDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageDTOBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  public PageDTOBuilder body(String body) {
    this.body = body;
    return this;
  }

  public PageDTOBuilder header(String header) {
    this.header = header;
    return this;
  }

  public PageDTOBuilder footer(String footer) {
    this.footer = footer;
    return this;
  }

  public PageDTOBuilder meta(String meta) {
    this.meta = meta;
    return this;
  }

  public PageDTOBuilder amp(String amp) {
    this.amp = amp;
    return this;
  }

  @Override
  public PageDTO build() {
    PageDTO pageDTO = new PageDTO();
    pageDTO.setBody(body);
    pageDTO.setCreationDate(creationDate);
    pageDTO.setFooter(footer);
    pageDTO.setMeta(meta);
    pageDTO.setHeader(header);
    pageDTO.setId(id);
    pageDTO.setMenuTitle(menuTitle);
    pageDTO.setModificationDate(modificationDate);
    pageDTO.setName(name);
    pageDTO.setAmp(amp);

    return pageDTO;
  }

  public static PageDTOBuilder create() {
    return new PageDTOBuilder();
  }

}
