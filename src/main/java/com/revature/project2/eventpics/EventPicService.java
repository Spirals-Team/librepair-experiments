package com.revature.project2.eventpics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventPicService {
  private final EventPicRepository eventPicRepository;

  @Autowired
  public EventPicService(EventPicRepository eventPicRepository) {
    this.eventPicRepository = eventPicRepository;
  }

  public Optional<EventPic> findByEventPicId(int id) {
    return eventPicRepository.findById(id);
  }

  public Iterable<EventPic> findAllEventPics() {
    return eventPicRepository.findAll();
  }
}
