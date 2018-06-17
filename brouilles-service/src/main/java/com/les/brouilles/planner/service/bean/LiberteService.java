package com.les.brouilles.planner.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.model.evenement.Liberte;
import com.les.brouilles.planner.persistence.repository.LiberteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LiberteService {

	@Autowired
	private LiberteRepository repository;

	@Autowired
	private CompteurService compteurService;

	@Transactional
	public Liberte createEvenement(final Liberte liberte) {
		log.info("Creation de l'evenement repas:{}", liberte);

		liberte.setNumeroEvenement("E-" + compteurService.getCompteur(TypeCompteur.EVENEMENT));

		final Liberte created = repository.save(liberte);
		return created;
	}

	@Transactional
	public void delete(final Long id) {
		log.info("Suprression de l'evenement liberte:{}", id);
		repository.delete(id);
	}

	@Transactional
	public Liberte update(final Liberte withUpdate) {
		return repository.save(withUpdate);
	}

	public Liberte getLiberteById(final Long id) {
		return repository.findOne(id);
	}

}
