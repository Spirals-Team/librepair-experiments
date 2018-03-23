package com.revature.project2.events;

import com.revature.project2.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class EventController {

  private final EventService eventService;

  private Map<String, String> message = new HashMap<>();

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
  public ResponseEntity createEvents(@RequestBody Event event) {
    Event e = eventService.saveEvents(event);
    Map<String, String> response = new HashMap<>();
    response.put("message", "Created event " + e.getId());
    return new ResponseEntity(response, HttpStatus.CREATED);
  }
  @GetMapping("api/event/attendee/{event_id}")
  public Set<User> getAttendee(@PathVariable int event_id){
    return eventService.getAttendees(event_id);
  }
  @GetMapping("/events/{eventId}")
  public ResponseEntity<Map<String, Object>> fetchEventById(@PathVariable int eventId) {
    Event e = eventService.findByEventId(eventId).orElse(null);
    if (e != null) {
      User u = e.getHost();
      Map<String, Object> response = new HashMap<>();
      response.put("event", e);
      response.put("host", u);
      return new ResponseEntity(response, HttpStatus.OK);
    } else
      return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @PutMapping("/api/events/{event_id}")
  public Event updateEventById(@PathVariable int event_id) {
    return eventService.updateEvent(event_id);
  }


}
