package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesChambreNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "La chambre avec id {0} n'a pas été trouvée";

	public BrouillesChambreNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}
