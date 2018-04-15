package com.isa.instaticketapi.service;

import java.util.ArrayList;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Milica
 * Service for hall in theaters or cinemas.
 *
 */

import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.places.HallDTO;

@Service
@Transactional
public class HallService {
	private final Logger log = LoggerFactory.getLogger(HallService.class);

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private PlaceRepository placerepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	/**
	 * 
	 * @param hallDTO
	 *            object providing information about new hall
	 * @param id
	 *            id of place
	 */
	public void createHall(HallDTO hallDTO, Long id) {
		Hall hall = new Hall();
		Place place = placerepository.findOneById(id);
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		hall.setCreatedBy(logged.getUsername());

		ArrayList<Hall> halls = hallRepository.findAllByPlace(place);

		for (int i = 0; i < halls.size(); i++) {
			if (halls.get(i).getName().equals(hallDTO.getName())) {
				throw new IllegalArgumentException("Validation error on name");
			}
		}

		hall.setName(hallDTO.getName());
		hall.setCol(hallDTO.getCol());
		hall.setRow(hallDTO.getRow());
		hall.setPlace(place);

		hallRepository.save(hall);
	}

	/**
	 * 
	 * @param id
	 *            id of hall for delete
	 * @return
	 */

	public Hall deleteHall(Long id) {
		Hall hall = hallRepository.findOneById(id);
		if (hall == null) {
			return null;
		}
		ArrayList<Projection> projections = projectionRepository.findAllByHall(hall);
		projectionRepository.delete(projections);

		hallRepository.delete(hall);
		log.debug("Deleted hall.");
		return hall;

	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return halls in place
	 */
	public ArrayList<Hall> getHalls(Long id) {
		Place place = placerepository.findOneById(id);

		if (place == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		ArrayList<Hall> halls = hallRepository.findAllByPlace(place);
		if (halls == null) {
			return null;
		}

		return halls;
	}

	/**
	 * 
	 * @param id
	 *            id of hall
	 * @return object hall
	 */
	public Hall getHall(Long id) {
		return hallRepository.findOneById(id);
	}

	/**
	 * 
	 * @param hallDTO
	 *            object hall for editing
	 * @param id
	 *            id of hall for change
	 * @return object hall
	 */
	public Hall editHall(HallDTO hallDTO, Long id) {
		Hall hall = hallRepository.findOneById(id);
		if (hall == null) {
			return null;
		}

		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		hall.setLastModifiedBy(logged.getUsername());
		hall.setName(hallDTO.getName());
		hall.setCol(hallDTO.getCol());
		hall.setRow(hallDTO.getRow());
		hallRepository.save(hall);
		return hall;
	}

	/**
	 * 
	 * @param id
	 *            id of hall
	 * @return list of objects (Projection) in hall
	 */
	public ArrayList<Projection> getAllProjectionInHall(Long id) {
		Hall hall = hallRepository.findOneById(id);
		if (hall == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		ArrayList<Projection> projections = projectionRepository.findAllByHall(hall);
		return projections;
	}
}
