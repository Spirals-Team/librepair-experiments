package com.isa.instaticketapi.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.Repertory.RepertoryDTO;

/**
 * Service for managing repertory
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class RepertoryService {
	
	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectionRepository projectionRepository;
	
	/**
	 * 
	 * @param repertoryDTO
	 *            object for creating
	 * @param id
	 *            id of place
	 */
	public void createRepertory(RepertoryDTO repertoryDTO, Long id) {
		Place place = placeRepository.findOneById(id);
		if (place == null) {
			throw new IllegalArgumentException("Invalid id of place!");
		}
		Repertory repertory = new Repertory();
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		repertory.setCreatedBy(logged.getUsername());

		repertory.setPlace(place);
		ArrayList<Repertory> repertories = repertoryRepository.findAllByPlace(place);

		for (int i = 0; i < repertories.size(); i++) {
			if ((repertories.get(i).getDate()).equals(repertoryDTO.getDate())) {
				throw new IllegalArgumentException("Repertory already exist!");
			}
		}
		repertory.setDate(repertoryDTO.getDate());

		repertoryRepository.save(repertory);

	}

	/**
	 * 
	 * @param id
	 *            id of repertory
	 * @return
	 */
	public Repertory deleteRepertory(Long id) {
		Repertory repertorty = repertoryRepository.findOneById(id);
		if (repertorty == null) {
			return null;
		}

		ArrayList<Projection> projections = projectionRepository.findAllByReperotry(repertorty);
		projectionRepository.delete(projections);

		repertoryRepository.delete(repertorty);

		return repertorty;
	}

	/**
	 * 
	 * @param id
	 *            id of repertory
	 * @return list objects
	 */
	public ArrayList<Projection> getAllProjectionsInRepertory(Long id) {
		Repertory repertory = repertoryRepository.findOneById(id);
		return projectionRepository.findAllByReperotry(repertory);

	}

}
