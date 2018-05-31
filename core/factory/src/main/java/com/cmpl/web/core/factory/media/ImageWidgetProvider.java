package com.cmpl.web.core.factory.media;

import java.util.*;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class ImageWidgetProvider implements WidgetProviderPlugin {

  private final MediaService mediaService;
  private final List<String> movieExtensions;

  public ImageWidgetProvider(MediaService mediaService) {
    this.mediaService = mediaService;
    this.movieExtensions = Arrays.asList("avi", "mp4", "flv", "mkv");
  }

  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {

    if (!StringUtils.hasText(widget.getEntityId())) {
      return new HashMap<>();
    }

    Map<String, Object> widgetModel = new HashMap<>();

    MediaDTO image = mediaService.getEntity(Long.parseLong(widget.getEntityId()));
    widgetModel.put("mediaUrl", image.getSrc());

    return widgetModel;
  }

  @Override
  public List<MediaDTO> getLinkableEntities() {
    List<MediaDTO> linkableImages = new ArrayList<>();

    List<MediaDTO> mediaEntities = mediaService.getEntities();
    mediaEntities.forEach(mediaEntity -> {
      if (!movieExtensions.contains(mediaEntity.getExtension())) {
        linkableImages.add(mediaEntity);
      }
    });

    return linkableImages;
  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }
    return "widgets/image";
  }

  @Override
  public String getWidgetType() {
    return "IMAGE";
  }

  @Override
  public String getTooltipKey() {
    return "widget.image.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
