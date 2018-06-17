package com.les.brouilles.planner.service.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.les.brouilles.planner.persistence.model.Compteur;
import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;
import com.les.brouilles.planner.persistence.repository.CompteurRepository;
import com.les.brouilles.planner.service.exception.BrouillesCompteurAbsentException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CompteurService {

	@Autowired
	private CompteurRepository repository;

	@Transactional
	public String getCompteur(final TypeCompteur typeCompteur) {

		final String annee = getYear(0);

		repository.updateCompteur(annee, typeCompteur);

		final Compteur compteur = repository.findByAnneeAndTypeCompteur(annee, typeCompteur);

		if (null == compteur) {
			throw new BrouillesCompteurAbsentException(annee, typeCompteur);
		}
		return compteur.getAnnee() + "-" + String.format("%04d", compteur.getValeur());
	}

	@Transactional
	public void insertCurrentAndNextYearCompteurIfNeeded() {

		for (final String annee : currentAndNextYear()) {
			for (final TypeCompteur typeCompteur : TypeCompteur.values()) {
				if (null == repository.findByAnneeAndTypeCompteur(annee, typeCompteur)) {
					log.info("Creation du compteur ({},{})", annee, typeCompteur);

					final Compteur compteur = Compteur.builder().annee(annee).typeCompteur(typeCompteur).valeur(0)
							.build();
					repository.save(compteur);
				}
			}
		}
	}

	private List<String> currentAndNextYear() {

		final List<String> list = new ArrayList<>();
		list.add(getYear(0));
		list.add(getYear(1));

		return list;
	}

	private String getYear(final int delta) {

		final Calendar now = Calendar.getInstance();
		final int year = now.get(Calendar.YEAR) + delta;
		return String.valueOf(year);
	}
}
