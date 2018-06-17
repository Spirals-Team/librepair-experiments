package com.les.brouilles.planner.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.les.brouilles.planner.persistence.model.devis.Devis;

public interface DevisRepository extends CrudRepository<Devis, Long> {

	List<Devis> findAllByOrderByNumeroDevisDesc();

	@Query("SELECT d FROM Devis d WHERE d.evenement.id = ?1 ORDER BY d.numeroDevis ASC")
	List<Devis> findAllByEvenementId(Long evenementId);

	@Query("SELECT d FROM Devis d JOIN d.evenement e WHERE e.client.id = ?1 ORDER BY d.numeroDevis ASC")
	List<Devis> findAllByClientId(Long clientId);
}