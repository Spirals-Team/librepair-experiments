package com.les.brouilles.planner.service.bean;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;
import com.les.brouilles.planner.persistence.model.evenement.enums.TypeEvenement;
import com.les.brouilles.planner.persistence.repository.CommonEvenementRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonEvenementService {

	@Autowired
	private CommonEvenementRepository evenementRepository;

	public List<CommonEvenement> getEvenements() {
		return evenementRepository.findAllByOrderByDebutAsc();
	}

	public List<CommonEvenement> getEvenementsByClientId(final Long clientId) {
		return evenementRepository.findEvenementsByClientId(clientId);
	}

	public List<CommonEvenement> getEvenementsInRange(final Date dateMin, final Date dateMax) {
		return evenementRepository.findByFinAfterAndDebutBeforeOrderByDebutAsc(dateMin, dateMax);
	}

	public List<CommonEvenement> getEvenementsDuJourAvecTypes(final Date debutJournee, final Date finJournee,
			final List<TypeEvenement> typesEvenements) {
		return evenementRepository.findByDebutAfterAndDebutBeforeAndTypeEvenementIn(debutJournee, finJournee,
				typesEvenements);
	}

	public CommonEvenement getEvenementById(final Long id) {

		return evenementRepository.findOne(id);
	}

	public List<CommonEvenement> findAllWithClientFetch() {
		return evenementRepository.findAllWithClientFetch();
	}

}
