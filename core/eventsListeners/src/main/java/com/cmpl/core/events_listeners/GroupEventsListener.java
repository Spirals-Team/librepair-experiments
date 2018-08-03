package com.cmpl.core.events_listeners;

import java.util.Objects;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.models.BOGroup;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.responsibility.ResponsibilityService;

public class GroupEventsListener {

  private final ResponsibilityService responsibilityService;

  public GroupEventsListener(ResponsibilityService responsibilityService) {
    this.responsibilityService = Objects.requireNonNull(responsibilityService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (BOGroup.class.equals(clazz)) {
      BOGroup deletedGroup = (BOGroup) deletedEvent.getEntity();
      if (deletedGroup != null) {
        String groupId = String.valueOf(deletedGroup.getId());
        responsibilityService.findByRoleId(groupId)
            .forEach(responsibilityDTO -> responsibilityService.deleteEntity(responsibilityDTO.getId()));

      }

    }
  }

}
