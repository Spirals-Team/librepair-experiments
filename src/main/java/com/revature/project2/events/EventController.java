package com.revature.project2.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class EventController {

  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/events")
  public Iterable<Event> fetchAllEvents() {
    return eventService.findAllEvents();
  }

  /**
   * Post : creates a new event
   *
   * @param event
   * @return
   */
  @PostMapping("/api/events")
  public Event createEvents(@RequestBody Event event) {
    return eventService.saveEvents(event);
  }

  @GetMapping("/api/events/{event_id}")
  public Event fetchEventById(@PathVariable int event_id) {
    return eventService.findByEventId(event_id).get();
  }

  @PutMapping("/api/events/{event_id}")
  public Event updateEventById(@PathVariable int event_id) {
    return eventService.updateEvent(event_id);
  }


}
