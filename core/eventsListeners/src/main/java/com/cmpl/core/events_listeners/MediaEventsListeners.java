package com.cmpl.core.events_listeners;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;

public class MediaEventsListeners {

  private final FileService fileService;

  public MediaEventsListeners(FileService fileService) {
    this.fileService = fileService;
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {

    Class<? extends BaseDTO> clazz = deletedEvent.getDto().getClass();
    if (MediaDTO.class.equals(clazz)) {
      MediaDTO deletedMedia = (MediaDTO) deletedEvent.getDto();

      if (deletedMedia != null) {
        fileService.removeMediaFromSystem(deletedMedia.getName());
      }
    }

  }
}
