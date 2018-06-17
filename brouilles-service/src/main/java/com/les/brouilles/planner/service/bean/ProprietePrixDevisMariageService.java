package com.les.brouilles.planner.service.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.les.brouilles.planner.persistence.model.devis.ProprietePrixDevisMariage;
import com.les.brouilles.planner.persistence.repository.ProprietesPrixDevisMariageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProprietePrixDevisMariageService {

	@Autowired
	private ProprietesPrixDevisMariageRepository repository;

	@Transactional
	public ProprietePrixDevisMariage create(final ProprietePrixDevisMariage prop) {
		log.info("Creation de la propriété devis mariage:{}", prop);

		final ProprietePrixDevisMariage created = repository.save(prop);
		return created;
	}

	@Transactional
	public ProprietePrixDevisMariage update(final ProprietePrixDevisMariage withUpdate) {

		return repository.save(withUpdate);
	}

	@Transactional
	public void delete(final Long id) {
		log.info("Suprression de l'evenement repas:{}", id);
		repository.delete(id);
	}

	public List<ProprietePrixDevisMariage> findAll() {
		return Lists.newArrayList(repository.findAll());
	}

	public List<ProprietePrixDevisMariage> findAllByOrderByOrderGroupAsc() {
		return repository.findAllByOrderByOrderGroupAsc();
	}

}
