package com.cmpl.web.core.style;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.Style;

public class StyleMapper extends BaseMapper<StyleDTO, Style> {

  private final MediaService mediaService;
  private final FileService fileService;

  public StyleMapper(MediaService mediaService, FileService fileService) {

    this.fileService = Objects.requireNonNull(fileService);
    this.mediaService = Objects.requireNonNull(mediaService);
  }

  @Override
  public StyleDTO toDTO(Style entity) {
    StyleDTO dto = StyleDTOBuilder.create().build();

    fillObject(entity, dto);
    if (!StringUtils.isEmpty(entity.getMediaId())) {
      MediaDTO media = mediaService.getEntity(Long.valueOf(entity.getMediaId()));

      if (media != null) {
        dto.setMedia(media);
        String content = readMediaContent(media);
        dto.setContent(content);
      }
    }
    return dto;
  }

  String readMediaContent(MediaDTO media) {
    return new BufferedReader(new InputStreamReader(fileService.read(media.getName()))).lines()
        .collect(Collectors.joining("\n"));
  }

  @Override
  public Style toEntity(StyleDTO dto) {
    Style entity = new Style();

    fillObject(dto, entity);
    entity.setMediaId(String.valueOf(dto.getMedia().getId()));
    return entity;
  }
}
