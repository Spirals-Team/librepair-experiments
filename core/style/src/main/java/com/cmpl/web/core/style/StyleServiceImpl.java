package com.cmpl.web.core.style;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;

public class StyleServiceImpl extends BaseServiceImpl<StyleDTO, Style> implements StyleService {

  private final StyleRepository styleRepository;
  private final MediaService mediaService;
  private final FileService fileService;

  public StyleServiceImpl(ApplicationEventPublisher publisher, StyleRepository styleRepository,
      MediaService mediaService, FileService fileService) {
    super(styleRepository, publisher);
    this.styleRepository = styleRepository;
    this.mediaService = mediaService;
    this.fileService = fileService;
  }

  @Override
  public StyleDTO getStyle() {
    List<Style> styles = styleRepository.findAll();
    if (CollectionUtils.isEmpty(styles)) {
      return null;
    }
    return toDTO(styles.get(0));
  }

  @Override
  public StyleDTO updateEntity(StyleDTO dto) {

    String content = dto.getContent();
    fileService.saveMediaOnSystem(dto.getMedia().getName(), content.getBytes());

    return toDTO(styleRepository.save(toEntity(dto)));

  }

  @Override
  public StyleDTO createEntity(StyleDTO dto) {
    mediaService.createEntity(dto.getMedia());
    String content = dto.getContent();
    fileService.saveMediaOnSystem(dto.getMedia().getName(), content.getBytes());

    return toDTO(styleRepository.save(toEntity(dto)));

  }

  @Override
  protected StyleDTO toDTO(Style entity) {
    StyleDTO dto = new StyleDTO();

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
    return new BufferedReader(new InputStreamReader(fileService.read(media.getName()))).lines().collect(
        Collectors.joining("\n"));
  }

  @Override
  protected Style toEntity(StyleDTO dto) {
    Style entity = new Style();

    fillObject(dto, entity);
    entity.setMediaId(String.valueOf(dto.getMedia().getId()));
    return entity;
  }

}
