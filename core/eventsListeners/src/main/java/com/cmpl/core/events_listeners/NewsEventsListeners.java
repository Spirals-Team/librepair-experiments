package com.cmpl.core.events_listeners;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.news.NewsContentDTO;
import com.cmpl.web.core.news.NewsContentService;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsImageDTO;
import com.cmpl.web.core.news.NewsImageService;

public class NewsEventsListeners {

  private final NewsContentService newsContentService;
  private final NewsImageService newsImageService;
  private final FileService fileService;

  public NewsEventsListeners(NewsContentService newsContentService, NewsImageService newsImageService,
      FileService fileService) {
    this.newsContentService = newsContentService;
    this.fileService = fileService;
    this.newsImageService = newsImageService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseDTO> clazz = deletedEvent.getDto().getClass();
    if (NewsEntryDTO.class.equals(clazz)) {

      NewsEntryDTO deletedNewsEntry = (NewsEntryDTO) deletedEvent.getDto();

      if (deletedNewsEntry != null) {
        NewsContentDTO deletedNewsContent = deletedNewsEntry.getNewsContent();
        if (deletedNewsContent != null) {
          newsContentService.deleteEntity(deletedNewsContent.getId());
        }
        NewsImageDTO deletedNewsImage = deletedNewsEntry.getNewsImage();
        if (deletedNewsImage != null) {
          newsImageService.deleteEntity(deletedNewsImage.getId());
          MediaDTO deletedNewsMedia = deletedNewsImage.getMedia();
          if (deletedNewsMedia != null) {
            fileService.removeMediaFromSystem(deletedNewsMedia.getName());
          }

        }

      }

    }
  }
}
