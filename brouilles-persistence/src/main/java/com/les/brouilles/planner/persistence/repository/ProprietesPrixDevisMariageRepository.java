package com.les.brouilles.planner.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.les.brouilles.planner.persistence.model.devis.ProprietePrixDevisMariage;

public interface ProprietesPrixDevisMariageRepository extends CrudRepository<ProprietePrixDevisMariage, Long> {

	List<ProprietePrixDevisMariage> findAllByOrderByOrderGroupAsc();
}