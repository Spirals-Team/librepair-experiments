package com.cmpl.core.events_listeners;

import java.util.Objects;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.User;
import com.cmpl.web.core.responsibility.ResponsibilityService;

public class UserEventsListeners {

  private final ResponsibilityService responsibilityService;

  public UserEventsListeners(ResponsibilityService responsibilityService) {
    this.responsibilityService = Objects.requireNonNull(responsibilityService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (User.class.equals(clazz)) {
      User deletedUser = (User) deletedEvent.getEntity();
      if (deletedUser != null) {
        responsibilityService.findByUserId(String.valueOf(deletedUser.getId()))
            .forEach(associationUserRoleDTO -> responsibilityService.deleteEntity(associationUserRoleDTO.getId()));
      }

    }
  }
}
