package com.les.brouilles.planner.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.les.brouilles.planner.persistence.model.Compteur;
import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;

public interface CompteurRepository extends CrudRepository<Compteur, Long> {

	Compteur findByAnneeAndTypeCompteur(final String annee, TypeCompteur TypeCompteur);

	@Modifying
	@Query("UPDATE Compteur SET valeur = valeur + 1 WHERE annee = ?1 AND typeCompteur = ?2")
	void updateCompteur(final String annee, TypeCompteur typeCompteur);

}