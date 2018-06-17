package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

import com.les.brouilles.planner.persistence.model.Compteur.TypeCompteur;

public class BrouillesCompteurAbsentException extends RuntimeException {

	private static final String MESSAGE = "Le compteur ({0},{1}) est absent en base. Veuillez l'insérer";

	public BrouillesCompteurAbsentException(final String annee, final TypeCompteur typeCompteur) {
		super(MessageFormat.format(MESSAGE, annee, typeCompteur));
	}
}
