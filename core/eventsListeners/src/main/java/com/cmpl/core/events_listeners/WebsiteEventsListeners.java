package com.cmpl.core.events_listeners;

import java.util.Objects;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.Website;
import com.cmpl.web.core.sitemap.SitemapService;

public class WebsiteEventsListeners {

  private final DesignService designService;
  private final SitemapService sitemapService;

  public WebsiteEventsListeners(DesignService designService, SitemapService sitemapService) {
    this.designService = Objects.requireNonNull(designService);
    this.sitemapService = Objects.requireNonNull(sitemapService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (Website.class.equals(clazz)) {
      Website deletedWebsite = (Website) deletedEvent.getEntity();
      if (deletedWebsite != null) {

        designService.findByWebsiteId(deletedWebsite.getId())
            .forEach(design -> designService.deleteEntity(design.getId()));
        sitemapService.findByWebsiteId(deletedWebsite.getId())
            .forEach(design -> sitemapService.deleteEntity(design.getId()));

      }

    }
  }
}
