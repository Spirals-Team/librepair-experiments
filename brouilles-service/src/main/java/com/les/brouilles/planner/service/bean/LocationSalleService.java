package com.les.brouilles.planner.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.model.evenement.LocationSalle;
import com.les.brouilles.planner.persistence.repository.LocationSalleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocationSalleService {

	@Autowired
	private LocationSalleRepository repository;

	@Autowired
	private CompteurService compteurService;

	public LocationSalle createEvenement(final LocationSalle location) {

		log.info("Creation de l'evenement location de salle :{}", location);

		location.setNumeroEvenement("E-" + compteurService.getCompteur(TypeCompteur.EVENEMENT));

		final LocationSalle created = repository.save(location);
		return created;
	}

	@Transactional
	public LocationSalle update(final LocationSalle withUpdate) {
		return repository.save(withUpdate);
	}

	public LocationSalle getLocationSalleById(final Long id) {
		return repository.findOne(id);
	}

}
