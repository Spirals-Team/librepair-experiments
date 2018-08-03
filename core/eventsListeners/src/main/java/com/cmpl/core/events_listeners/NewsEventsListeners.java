package com.cmpl.core.events_listeners;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageDTO;
import com.cmpl.web.core.news.image.NewsImageService;

public class NewsEventsListeners {

  private final NewsContentService newsContentService;
  private final NewsImageService newsImageService;

  public NewsEventsListeners(NewsContentService newsContentService, NewsImageService newsImageService) {
    this.newsContentService = Objects.requireNonNull(newsContentService);
    this.newsImageService = Objects.requireNonNull(newsImageService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (NewsEntry.class.equals(clazz)) {

      NewsEntry deletedNewsEntry = (NewsEntry) deletedEvent.getEntity();

      if (deletedNewsEntry != null) {
        String contentId = deletedNewsEntry.getContentId();
        if (StringUtils.hasText(contentId)) {
          newsContentService.deleteEntity(Long.parseLong(contentId));
        }
        String imageId = deletedNewsEntry.getImageId();
        if (StringUtils.hasText(imageId)) {
          NewsImageDTO deletedNewsImage = newsImageService.getEntity(Long.parseLong(imageId));
          newsImageService.deleteEntity(deletedNewsImage.getId());
        }
      }

    }
  }
}
