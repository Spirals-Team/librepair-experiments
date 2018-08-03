package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Sitemap;

public class SitemapMapper extends BaseMapper<SitemapDTO, Sitemap> {

  @Override
  public SitemapDTO toDTO(Sitemap entity) {
    if (entity == null) {
      return null;
    }
    SitemapDTO dto = SitemapDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Sitemap toEntity(SitemapDTO dto) {
    Sitemap entity = SitemapBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
