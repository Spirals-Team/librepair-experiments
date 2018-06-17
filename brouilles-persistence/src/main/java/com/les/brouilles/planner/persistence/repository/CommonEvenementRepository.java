package com.les.brouilles.planner.persistence.repository;

import java.util.Date;
import java.util.List;

import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;
import com.les.brouilles.planner.persistence.model.evenement.enums.TypeEvenement;

public interface CommonEvenementRepository extends EvenementBaseRepository<CommonEvenement> {

	// On filtre tel que fin >=dateMin et debut <= dateMax
	List<CommonEvenement> findByFinAfterAndDebutBeforeOrderByDebutAsc(Date dateMin, Date dateMax);

	// Evenements du jour
	List<CommonEvenement> findByDebutAfterAndDebutBeforeAndTypeEvenementIn(Date debutJournee, Date finJournee,
			List<TypeEvenement> typesEvenements);

}