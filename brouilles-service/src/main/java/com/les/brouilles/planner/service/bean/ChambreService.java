package com.les.brouilles.planner.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.model.evenement.Chambre;
import com.les.brouilles.planner.persistence.repository.ChambreRepository;
import com.les.brouilles.planner.service.mapper.evenement.ChambreMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChambreService {

	@Autowired
	private ChambreRepository repository;

	@Autowired
	private CompteurService compteurService;

	@Autowired
	private ChambreMapper chambreMapper;

	@Transactional
	public Chambre createEvenement(final Chambre chambre) {
		log.info("Creation de l'evenement chambre:{}", chambre);
		chambre.setNumeroEvenement("E-" + compteurService.getCompteur(TypeCompteur.EVENEMENT));

		final Chambre created = repository.save(chambre);
		return created;
	}

	public Chambre getChambreById(final Long id) {
		return repository.findOne(id);
	}

	public Chambre update(final Chambre withUpdate) {
		return repository.save(withUpdate);
	}
}
