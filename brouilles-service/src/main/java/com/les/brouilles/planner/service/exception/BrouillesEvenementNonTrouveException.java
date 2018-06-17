package com.les.brouilles.planner.service.exception;

import java.text.MessageFormat;

public class BrouillesEvenementNonTrouveException extends RuntimeException {

	private static final String MESSAGE = "L'événement avec id {0} n'a pas été trouvé";

	public BrouillesEvenementNonTrouveException(final Long id) {
		super(MessageFormat.format(MESSAGE, id));
	}
}
