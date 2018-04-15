package com.isa.instaticketapi.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.places.ChangeEventDTO;
import com.isa.instaticketapi.service.dto.places.EventDTO;

/**
 * Service for managing event.
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class EventService {
	private final Logger log = LoggerFactory.getLogger(EventService.class);

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	/**
	 * 
	 * @param eventDTO
	 *            object providing information about new event
	 */
	public void createEvent(EventDTO eventDTO, Long id) {
		Event event = new Event();
		Place place = placeRepository.findOneById(id);

		ArrayList<Event> eventsInPlace = eventRepository.findAllByPlace(place);

		for (int i = 0; i < eventsInPlace.size(); i++) {
			if ((eventsInPlace.get(i).getName()).equals(eventDTO.getName())) {
				throw new IllegalArgumentException("Validation error on name");
			}
		}
		event.setPlace(place);
		event.setName(eventDTO.getName());
		event.setActors(eventDTO.getActors());
		event.setDirector(eventDTO.getDirector());
		event.setDuration(eventDTO.getDuration());
		event.setDescription(eventDTO.getDescription());
		event.setType(eventDTO.getType());
		event.setImageUrl(eventDTO.getImageUrl());
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		event.setCreatedBy(logged.getUsername());

		eventRepository.save(event);

	}

	/**
	 * 
	 * @param id
	 * @return object event
	 */

	public Event getEvent(Long id) {
		if (eventRepository.findOneById(id) == null) {
			return null;
		}
		return eventRepository.findOneById(id);
	}

	/**
	 * 
	 * @param ChangeEventDTO
	 *            object for editing
	 * @param id
	 *            id of object
	 */

	public Event changeEvent(ChangeEventDTO changeProjectionDTO, long id) {
		Event event = eventRepository.findOneById(id);
		if (event == null) {
			return null;
		}
		event.setName(changeProjectionDTO.getName());
		event.setActors(changeProjectionDTO.getActors());
		event.setDescription(changeProjectionDTO.getDescription());
		event.setDirector(changeProjectionDTO.getDirector());
		event.setDuration(changeProjectionDTO.getDuration());
		event.setType(changeProjectionDTO.getType());
		event.setImageUrl(changeProjectionDTO.getImageUrl());
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		event.setLastModifiedBy(logged.getUsername());

		eventRepository.save(event);
		return event;
	}

	/**
	 * Deleting event from database
	 * 
	 * @param id
	 *            representing id of event which will be deleted
	 */
	public Event deleteEvent(Long id) {
		Event event = eventRepository.findOneById(id);
		if (event == null) {
			return null;
		}
		ArrayList<Projection> projections = projectionRepository.findAllByEvent(event);
		projectionRepository.delete(projections);

		eventRepository.delete(event);
		log.debug("Deleted event.");
		return event;

	}
}
