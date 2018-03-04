package pl.wasper.bandmanagement.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.event.service.EventService;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> eventList() {
        return service.getEvents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EventDto findEvent(@PathVariable Long id) throws ElementNotFoundException {
        return service.findOneById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto addEvent(@RequestBody EventDto event) {
        return service.save(event);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent(@RequestBody EventDto event) throws ElementNotFoundException {
        return service.update(event);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeEvent(@PathVariable Long id) throws ElementNotFoundException {
        service.delete(id);
    }
}
