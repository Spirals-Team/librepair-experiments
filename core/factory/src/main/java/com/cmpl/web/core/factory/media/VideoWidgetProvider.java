package com.cmpl.web.core.factory.media;

import java.util.*;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class VideoWidgetProvider implements WidgetProviderPlugin {

  private final MediaService mediaService;
  private final List<String> movieExtensions;

  public VideoWidgetProvider(MediaService mediaService) {
    this.mediaService = mediaService;
    this.movieExtensions = Arrays.asList("avi", "mp4", "flv", "mkv");
  }

  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {
    if (!StringUtils.hasText(widget.getEntityId())) {
      return new HashMap<>();
    }

    Map<String, Object> widgetModel = new HashMap<>();

    MediaDTO video = mediaService.getEntity(Long.parseLong(widget.getEntityId()));
    widgetModel.put("mediaUrl", video.getSrc());

    return widgetModel;
  }

  @Override
  public List<MediaDTO> getLinkableEntities() {
    List<MediaDTO> linkableVideos = new ArrayList<>();
    List<MediaDTO> mediaEntities = mediaService.getEntities();
    mediaEntities.forEach(mediaEntity -> {
      if (movieExtensions.contains(mediaEntity.getExtension())) {
        linkableVideos.add(mediaEntity);
      }
    });

    return linkableVideos;
  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }
    return "widgets/video";
  }

  @Override
  public String getWidgetType() {
    return "VIDEO";
  }

  @Override
  public String getTooltipKey() {
    return "widget.video.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
