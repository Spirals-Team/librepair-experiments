package com.les.brouilles.planner.persistence.exception;

import java.text.MessageFormat;
import java.util.Date;

public class BrouillesPersistenceInvalidDatesDebutFinException extends RuntimeException {

	private static final long serialVersionUID = 5629270178409397884L;

	private static final String MESSAGE = "La date de début {0} doit être antérieure à la date de fin {1}";

	public BrouillesPersistenceInvalidDatesDebutFinException(final Date debut, final Date fin) {
		super(MessageFormat.format(MESSAGE, debut, fin));
	}
}
