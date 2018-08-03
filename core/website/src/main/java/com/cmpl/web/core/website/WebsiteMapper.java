package com.cmpl.web.core.website;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Website;

public class WebsiteMapper extends BaseMapper<WebsiteDTO, Website> {
  @Override
  public WebsiteDTO toDTO(Website entity) {
    if (entity == null) {
      return null;
    }
    WebsiteDTO dto = WebsiteDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Website toEntity(WebsiteDTO dto) {
    Website entity = WebsiteBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
