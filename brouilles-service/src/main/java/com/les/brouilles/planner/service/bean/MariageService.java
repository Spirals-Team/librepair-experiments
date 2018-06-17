package com.les.brouilles.planner.service.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.model.evenement.Mariage;
import com.les.brouilles.planner.persistence.repository.MariageRepository;
import com.les.brouilles.planner.service.mapper.evenement.MariageMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MariageService {

	@Autowired
	private MariageRepository repository;

	@Autowired
	private MariageMapper mapper;

	@Autowired
	private CompteurService compteurService;

	@Transactional
	public Mariage createEvenement(final Mariage mariage) {
		log.info("Creation de l'evenement mariage:{}", mariage);

		mariage.setNumeroEvenement("E-" + compteurService.getCompteur(TypeCompteur.EVENEMENT));

		final Mariage created = repository.save(mariage);
		return created;
	}

	public Mariage getMariageById(final Long id) {

		return repository.findOne(id);

	}

	@Transactional
	public Mariage updateMariage(final Mariage withUpdate) {

		return repository.save(withUpdate);
	}

	public List<Mariage> getAllMariages() {

		return Lists.newArrayList(repository.findAll());
	}
}
