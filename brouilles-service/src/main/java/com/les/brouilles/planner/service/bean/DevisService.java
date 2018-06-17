package com.les.brouilles.planner.service.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.les.brouilles.planner.persistence.model.devis.Devis;
import com.les.brouilles.planner.persistence.repository.DevisRepository;
import com.les.brouilles.planner.service.devis.json.GenerateurNumeroDevis;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DevisService {

	@Autowired
	private DevisRepository repository;

	@Autowired
	private GenerateurNumeroDevis generateurNumeroDevis;

	@Transactional
	public Devis create(final Devis entity) {

		entity.setNumeroDevis(generateurNumeroDevis.generateFor(entity));

		final Devis created = repository.save(entity);
		return created;
	}

	public List<Devis> getAllDevis() {
		return repository.findAllByOrderByNumeroDevisDesc();
	}

	public List<Devis> findByEvenementId(final Long evenementId) {
		return repository.findAllByEvenementId(evenementId);
	}

	public Devis findById(final Long id) {
		return repository.findOne(id);
	}

	public List<Devis> findByClientId(final Long clientId) {
		return repository.findAllByClientId(clientId);
	}

	@Transactional
	public Devis update(final Devis withUpdate) {
		return repository.save(withUpdate);
	}

}
