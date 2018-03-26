package com.revature.project2.events;

import com.revature.project2.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

  private EventRepository eventRepository;

  public EventService() {
  }

  @Autowired
  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Optional<Event> findByEventId(int id) {

    return eventRepository.findById(id);
  }
  public Set<User> getAttendees(int id){
    return eventRepository.getAttendeesById(id);
  }
  public Iterable<Event> findAllEvents() {
    return eventRepository.findAll();
  }

  public Event saveEvents(Event event) {
    return eventRepository.save(event);
  }

  public Event updateEvent(int id){
    Event event = eventRepository.findById(id).get();
    return eventRepository.save(event);
  }

}
