package pl.wasper.bandmanagement.event.service;

import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;

import java.util.List;

public interface EventService {
    List<EventDto> getEvents();
    EventDto findOneById(Long id) throws ElementNotFoundException;
    EventDto save(EventDto event);
    EventDto update(EventDto event) throws ElementNotFoundException;
    void delete(Long id) throws ElementNotFoundException;
}
