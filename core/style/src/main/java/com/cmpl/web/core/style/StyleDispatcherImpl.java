package com.cmpl.web.core.style;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;

public class StyleDispatcherImpl implements StyleDispatcher {

  private final StyleService styleService;
  private final StyleTranslator translator;

  public StyleDispatcherImpl(StyleService styleService, StyleTranslator translator) {
    this.styleService = Objects.requireNonNull(styleService);
    this.translator = Objects.requireNonNull(translator);

  }

  @Override
  public StyleResponse updateEntity(StyleUpdateForm form, Locale locale) {
    StyleDTO dto = translator.fromUpdateFormToDTO(form);
    StyleDTO updatedDTO = styleService.updateEntity(dto);
    return translator.fromDTOToResponse(updatedDTO);
  }

  @Override
  public StyleResponse createEntity(StyleCreateForm form, Locale locale) {
    StyleDTO dto = translator.fromCreateFormToDTO(form);
    dto.setMedia(initMedia(form.getName()));
    return translator.fromDTOToResponse(styleService.createEntity(dto));
  }

  private MediaDTO initMedia(String styleName) {
    return MediaDTOBuilder.create().name(styleName + ".css").extension(".css").size(0l).contentType("text/css")
        .src("/public/media/" + styleName + ".css").id(Math.abs(UUID.randomUUID().getLeastSignificantBits())).build();
  }

  @Override
  public StyleResponse deleteEntity(String styleId, Locale locale) {
    styleService.deleteEntity(Long.parseLong(styleId));
    return StyleResponseBuilder.create().build();
  }

}
