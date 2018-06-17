package com.les.brouilles.planner.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.model.evenement.Repas;
import com.les.brouilles.planner.persistence.repository.RepasRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RepasService {

	@Autowired
	private RepasRepository repository;

	@Autowired
	private CompteurService compteurService;

	@Transactional
	public Repas createEvenement(final Repas repas) {
		log.info("Creation de l'evenement repas:{}", repas);

		repas.setNumeroEvenement("E-" + compteurService.getCompteur(TypeCompteur.EVENEMENT));

		final Repas created = repository.save(repas);
		return created;
	}

	public Repas getRepasById(final Long id) {

		return repository.findOne(id);

	}

	@Transactional
	public Repas updateRepas(final Repas withUpdate) {

		return repository.save(withUpdate);
	}

	@Transactional
	public void delete(final Long id) {
		log.info("Suprression de l'evenement repas:{}", id);
		repository.delete(id);
	}

}
