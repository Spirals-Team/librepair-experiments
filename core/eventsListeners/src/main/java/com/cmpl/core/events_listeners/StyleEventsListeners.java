package com.cmpl.core.events_listeners;

import java.util.Objects;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.Style;

public class StyleEventsListeners {

  private final DesignService designService;

  public StyleEventsListeners(DesignService designService) {
    this.designService = Objects.requireNonNull(designService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (Style.class.equals(clazz)) {
      Style deletedStyle = (Style) deletedEvent.getEntity();
      if (deletedStyle != null) {

        designService.findByStyleId(deletedStyle.getId()).forEach(design -> designService.deleteEntity(design.getId()));

      }

    }
  }

}
